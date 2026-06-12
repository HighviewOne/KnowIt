package com.knowit.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.knowit.data.HighScoreRepository
import com.knowit.model.QuestionType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repo: HighScoreRepository
    private lateinit var vm: GameViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repo = mockk(relaxed = true)
        coEvery { repo.getHighScore() } returns 0
        vm = GameViewModel(SavedStateHandle(), repo)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `startGame transitions to PLAYING with 20 questions`() {
        vm.startGame()
        with(vm.state.value) {
            assertEquals(GamePhase.PLAYING, phase)
            assertEquals(20, questions.size)
            assertEquals(0, currentQuestionIndex)
            assertEquals(0, score)
            assertEquals(0, streak)
            assertEquals(AnswerStatus.NONE, answerStatus)
        }
    }

    @Test
    fun `first correct MC answer awards 10 points and increments streak`() {
        vm.startGame()
        val q = vm.state.value.questions[0]

        vm.submitMultipleChoiceAnswer(q.correctAnswer)

        with(vm.state.value) {
            assertEquals(AnswerStatus.CORRECT, answerStatus)
            assertEquals(10, score)
            assertEquals(10, lastPointsAwarded)
            assertEquals(1, streak)
            assertEquals(1, correctCount)
        }
    }

    @Test
    fun `wrong MC answer awards 0 points and resets streak`() {
        vm.startGame()
        val q = vm.state.value.questions[0]
        val wrong = q.options.first { it != q.correctAnswer }

        vm.submitMultipleChoiceAnswer(wrong)

        with(vm.state.value) {
            assertEquals(AnswerStatus.WRONG, answerStatus)
            assertEquals(0, score)
            assertEquals(0, lastPointsAwarded)
            assertEquals(0, streak)
            assertEquals(0, correctCount)
        }
    }

    @Test
    fun `second consecutive correct answer awards streak bonus`() {
        vm.startGame()
        val q0 = vm.state.value.questions[0]
        require(q0.type == QuestionType.MULTIPLE_CHOICE)

        vm.submitMultipleChoiceAnswer(q0.correctAnswer) // score=10, streak=1
        vm.advanceToNextQuestion()

        // questions[1] is TYPE_IN, correctAnswer="1945"
        val q1 = vm.state.value.questions[1]
        require(q1.type == QuestionType.TYPE_IN)
        vm.updateTypeInText(q1.correctAnswer)
        vm.submitTypeInAnswer() // streak=1 → bonus, +15 pts

        with(vm.state.value) {
            assertEquals(AnswerStatus.CORRECT, answerStatus)
            assertEquals(25, score)
            assertEquals(15, lastPointsAwarded)
            assertEquals(2, streak)
        }
    }

    @Test
    fun `wrong answer breaks streak`() {
        vm.startGame()
        val q0 = vm.state.value.questions[0]
        vm.submitMultipleChoiceAnswer(q0.correctAnswer) // streak=1
        vm.advanceToNextQuestion()

        val q1 = vm.state.value.questions[1]
        require(q1.type == QuestionType.TYPE_IN)
        vm.updateTypeInText("totally wrong")
        vm.submitTypeInAnswer()

        assertEquals(0, vm.state.value.streak)
    }

    @Test
    fun `submitting answer twice is ignored`() {
        vm.startGame()
        val q = vm.state.value.questions[0]

        vm.submitMultipleChoiceAnswer(q.correctAnswer)
        vm.submitMultipleChoiceAnswer(q.correctAnswer)

        assertEquals(10, vm.state.value.score)
    }

    @Test
    fun `type-in answer matches case-insensitively`() {
        vm.startGame()
        vm.advanceToNextQuestion() // questions[1]: TYPE_IN, "1945"

        vm.updateTypeInText("1945")
        vm.submitTypeInAnswer()

        assertEquals(AnswerStatus.CORRECT, vm.state.value.answerStatus)
    }

    @Test
    fun `type-in accepts alternate accepted answers`() {
        vm.startGame()
        // Skip to questions[3]: TYPE_IN, "Who played Iron Man?" acceptedAnswers includes "rdj"
        repeat(3) { vm.advanceToNextQuestion() }

        vm.updateTypeInText("RDJ") // alternate accepted answer, case-insensitive
        vm.submitTypeInAnswer()

        assertEquals(AnswerStatus.CORRECT, vm.state.value.answerStatus)
    }

    @Test
    fun `blank type-in is not submitted`() {
        vm.startGame()
        vm.advanceToNextQuestion() // TYPE_IN question

        vm.updateTypeInText("   ")
        vm.submitTypeInAnswer()

        assertEquals(AnswerStatus.NONE, vm.state.value.answerStatus)
    }

    @Test
    fun `game transitions to GAME_OVER after all questions answered`() = runTest {
        vm.startGame()
        answerAllCorrectly()
        assertEquals(GamePhase.GAME_OVER, vm.state.value.phase)
    }

    @Test
    fun `high score is persisted when game ends`() = runTest {
        vm.startGame()
        answerAllCorrectly()
        coVerify { repo.saveHighScore(any()) }
    }

    @Test
    fun `max score for 20 questions is 295`() = runTest {
        vm.startGame()
        answerAllCorrectly()
        assertEquals(295, vm.state.value.highScore)
    }

    @Test
    fun `goHome resets phase to HOME`() {
        vm.startGame()
        vm.goHome()
        assertEquals(GamePhase.HOME, vm.state.value.phase)
    }

    private fun answerAllCorrectly() {
        val questions = vm.state.value.questions
        questions.forEach { q ->
            when (q.type) {
                QuestionType.MULTIPLE_CHOICE -> vm.submitMultipleChoiceAnswer(q.correctAnswer)
                QuestionType.TYPE_IN -> {
                    vm.updateTypeInText(q.correctAnswer)
                    vm.submitTypeInAnswer()
                }
            }
            vm.advanceToNextQuestion()
        }
    }
}
