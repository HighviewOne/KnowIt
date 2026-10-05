package com.knowit.viewmodel

import com.knowit.data.HighScoreRepository
import com.knowit.data.questionBank
import com.knowit.model.Category
import com.knowit.model.Question
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

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
        vm = GameViewModel(repo)
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

        // questions alternate MC / TYPE_IN, so questions[1] is TYPE_IN
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
        startFixtureGame()
        answerCurrent(correct = true)
        vm.advanceToNextQuestion() // fixture questions[1]: TYPE_IN "Robert Downey Jr"

        vm.updateTypeInText("  robert DOWNEY jr ")
        vm.submitTypeInAnswer()

        assertEquals(AnswerStatus.CORRECT, vm.state.value.answerStatus)
    }

    @Test
    fun `type-in ignores punctuation`() {
        startFixtureGame()
        answerCurrent(correct = true)
        vm.advanceToNextQuestion()

        vm.updateTypeInText("Robert Downey, Jr.")
        vm.submitTypeInAnswer()

        assertEquals(AnswerStatus.CORRECT, vm.state.value.answerStatus)
    }

    @Test
    fun `type-in accepts alternate accepted answers`() {
        startFixtureGame()
        answerCurrent(correct = true)
        vm.advanceToNextQuestion()

        vm.updateTypeInText("RDJ") // alternate accepted answer, case-insensitive
        vm.submitTypeInAnswer()

        assertEquals(AnswerStatus.CORRECT, vm.state.value.answerStatus)
    }

    @Test
    fun `blank type-in is not submitted`() {
        startFixtureGame()
        answerCurrent(correct = true)
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
    fun `startGame draws 20 distinct bank questions with formats alternating`() {
        vm.startGame()
        val questions = vm.state.value.questions
        val bankIds = questionBank.map { it.id }.toSet()

        assertEquals(GameViewModel.QUESTIONS_PER_GAME, questions.size)
        assertEquals(questions.size, questions.map { it.id }.toSet().size)
        assertTrue(questions.all { it.id in bankIds })
        questions.forEachIndexed { i, q ->
            val expected = if (i % 2 == 0) QuestionType.MULTIPLE_CHOICE else QuestionType.TYPE_IN
            assertEquals("question $i", expected, q.type)
        }
    }

    @Test
    fun `question order differs between seeds`() {
        val orderA = GameViewModel(repo, random = Random(1))
            .apply { startGame() }.state.value.questions.map { it.id }
        val orderB = GameViewModel(repo, random = Random(2))
            .apply { startGame() }.state.value.questions.map { it.id }

        assertTrue(orderA != orderB)
    }

    @Test
    fun `consecutive games draw different question sets`() {
        val seeded = GameViewModel(repo, random = Random(42))
        seeded.startGame()
        val first = seeded.state.value.questions.map { it.id }.toSet()
        seeded.startGame()
        val second = seeded.state.value.questions.map { it.id }.toSet()

        assertTrue(first != second)
    }

    @Test
    fun `advancing before answering is ignored`() {
        vm.startGame()
        vm.advanceToNextQuestion()

        assertEquals(0, vm.state.value.currentQuestionIndex)
    }

    @Test
    fun `advancing after game over does not save again`() = runTest {
        vm.startGame()
        answerAllCorrectly()
        vm.advanceToNextQuestion()

        coVerify(exactly = 1) { repo.saveHighScore(any()) }
    }

    @Test
    fun `beating the previous best flags a new high score`() = runTest {
        coEvery { repo.getHighScore() } returns 100
        vm = GameViewModel(repo)
        vm.startGame()
        answerAllCorrectly()

        assertTrue(vm.state.value.isNewHighScore)
        assertEquals(295, vm.state.value.highScore)
    }

    @Test
    fun `tying the previous best is not a new high score`() = runTest {
        coEvery { repo.getHighScore() } returns 295
        vm = GameViewModel(repo)
        vm.startGame()
        answerAllCorrectly()

        assertFalse(vm.state.value.isNewHighScore)
    }

    @Test
    fun `scoring below the previous best keeps it`() = runTest {
        coEvery { repo.getHighScore() } returns 295
        vm = GameViewModel(repo)
        vm.startGame()
        repeat(vm.state.value.questions.size) {
            answerCurrent(correct = false)
            vm.advanceToNextQuestion()
        }

        with(vm.state.value) {
            assertEquals(GamePhase.GAME_OVER, phase)
            assertFalse(isNewHighScore)
            assertEquals(295, highScore)
        }
    }

    @Test
    fun `goHome resets phase to HOME`() {
        vm.startGame()
        vm.goHome()
        assertEquals(GamePhase.HOME, vm.state.value.phase)
    }

    private fun answerAllCorrectly() {
        repeat(vm.state.value.questions.size) {
            answerCurrent(correct = true)
            vm.advanceToNextQuestion()
        }
    }

    private fun answerCurrent(correct: Boolean) {
        val q = vm.state.value.questions[vm.state.value.currentQuestionIndex]
        when (q.type) {
            QuestionType.MULTIPLE_CHOICE -> vm.submitMultipleChoiceAnswer(
                if (correct) q.correctAnswer else q.options.first { it != q.correctAnswer }
            )
            QuestionType.TYPE_IN -> {
                vm.updateTypeInText(if (correct) q.correctAnswer else "definitely wrong")
                vm.submitTypeInAnswer()
            }
        }
    }

    /** One MC + one type-in question; interleaving makes the order deterministic. */
    private fun startFixtureGame() {
        val fixture = listOf(
            Question(
                id = 1,
                type = QuestionType.MULTIPLE_CHOICE,
                category = Category.SCIENCE,
                questionText = "What is the chemical symbol for gold?",
                correctAnswer = "Au",
                options = listOf("Au", "Ag", "Fe", "Gd")
            ),
            Question(
                id = 2,
                type = QuestionType.TYPE_IN,
                category = Category.POP_CULTURE,
                questionText = "Who played Iron Man in the Marvel Cinematic Universe?",
                correctAnswer = "Robert Downey Jr",
                acceptedAnswers = listOf("rdj")
            )
        )
        vm = GameViewModel(repo, questionSource = fixture)
        vm.startGame()
    }
}
