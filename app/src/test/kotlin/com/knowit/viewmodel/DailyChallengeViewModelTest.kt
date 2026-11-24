package com.knowit.viewmodel

import com.knowit.data.DailyRepository
import com.knowit.data.DailySave
import com.knowit.data.HighScoreRepository
import com.knowit.data.questionBank
import com.knowit.model.DailyChallenge
import com.knowit.model.Outcome
import com.knowit.model.QuestionType
import com.knowit.model.Scoring
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class DailyChallengeViewModelTest {

    private val today = LocalDate.of(2026, 12, 1)
    private val epochDay = today.toEpochDay()
    private lateinit var highScores: HighScoreRepository
    private lateinit var dailyRepo: DailyRepository
    private val saved = mutableListOf<DailySave>()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        highScores = mockk(relaxed = true)
        coEvery { highScores.getHighScore() } returns 100
        dailyRepo = mockk(relaxed = true)
        val captured = slot<DailySave>()
        coEvery { dailyRepo.save(capture(captured)) } answers { saved += captured.captured }
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    private fun viewModel(save: DailySave = DailySave()): GameViewModel {
        coEvery { dailyRepo.load() } returns save
        return GameViewModel(highScores, dailyRepo, today = { today })
    }

    private fun GameViewModel.current() = state.value.questions[state.value.currentQuestionIndex]

    private fun GameViewModel.answer(correct: Boolean) {
        val q = current()
        when (q.type) {
            QuestionType.MULTIPLE_CHOICE -> submitMultipleChoiceAnswer(
                if (correct) q.correctAnswer else state.value.shuffledOptions.first { it != q.correctAnswer && it !in state.value.hiddenOptions }
            )
            QuestionType.TYPE_IN -> {
                updateTypeInText(if (correct) q.correctAnswer else "definitely wrong")
                submitTypeInAnswer()
            }
        }
    }

    @Test
    fun `startDaily plays today's ten questions`() {
        val vm = viewModel()
        vm.startDaily()
        with(vm.state.value) {
            assertEquals(GamePhase.PLAYING, phase)
            assertEquals(GameMode.DAILY, mode)
            assertEquals(DailyChallenge.questionsFor(today, questionBank), questions)
            assertEquals(DailyChallenge.numberFor(today), dailyNumber)
            assertTrue(fiftyFiftyAvailable)
            assertEquals(DailyChallenge.optionOrder(questions[0], today, 0), shuffledOptions)
        }
    }

    @Test
    fun `every answer is saved so the run can resume`() {
        val vm = viewModel()
        vm.startDaily()
        vm.answer(correct = true)
        assertEquals(DailySave(day = epochDay, outcomes = listOf(Outcome.CORRECT)), saved.last())
        assertEquals(1, vm.state.value.daily.answered)
    }

    @Test
    fun `a saved run resumes at the next question with its score`() {
        val outcomes = listOf(Outcome.CORRECT, Outcome.CORRECT, Outcome.WRONG)
        val vm = viewModel(DailySave(day = epochDay, outcomes = outcomes, fiftyFiftyIndex = 0))
        assertTrue(vm.state.value.daily.isInProgress)
        vm.startDaily()
        with(vm.state.value) {
            assertEquals(GamePhase.PLAYING, phase)
            assertEquals(3, currentQuestionIndex)
            assertEquals(25, score)
            assertEquals(0, streak)
            assertEquals(2, correctCount)
            assertFalse(fiftyFiftyAvailable)
        }
    }

    @Test
    fun `yesterday's progress is ignored`() {
        val vm = viewModel(DailySave(day = epochDay - 1, outcomes = listOf(Outcome.WRONG)))
        vm.startDaily()
        assertEquals(0, vm.state.value.currentQuestionIndex)
        assertEquals(0, vm.state.value.daily.answered)
    }

    @Test
    fun `finishing completes the day, extends the streak and leaves the high score alone`() {
        val vm = viewModel(DailySave(lastCompletedDay = epochDay - 1, streak = 3, bestStreak = 3))
        vm.startDaily()
        repeat(DailyChallenge.QUESTION_COUNT) {
            vm.answer(correct = true)
            vm.advanceToNextQuestion()
        }
        with(vm.state.value) {
            assertEquals(GamePhase.GAME_OVER, phase)
            assertEquals(Scoring.maxScore(10), score)
            assertFalse(isNewHighScore)
            assertEquals(100, highScore)
            assertEquals(4, daily.streak)
            assertTrue(daily.isDone)
        }
        with(saved.last()) {
            assertEquals(epochDay, lastCompletedDay)
            assertEquals(4, streak)
            assertEquals(4, bestStreak)
        }
        coVerify(exactly = 0) { highScores.saveHighScore(any()) }
    }

    @Test
    fun `a finished day opens straight to its result`() {
        val outcomes = List(10) { if (it == 4) Outcome.CLOSE else Outcome.CORRECT }
        val vm = viewModel(DailySave(day = epochDay, outcomes = outcomes, lastCompletedDay = epochDay, streak = 1, bestStreak = 1))
        vm.startDaily()
        with(vm.state.value) {
            assertEquals(GamePhase.GAME_OVER, phase)
            assertEquals(GameMode.DAILY, mode)
            assertEquals(outcomes, this.outcomes)
            assertEquals(Scoring.tally(outcomes).first, score)
        }
    }

    @Test
    fun `fifty-fifty hides two wrong options once`() {
        val vm = viewModel()
        vm.startDaily()
        val q = vm.current()
        assertEquals(QuestionType.MULTIPLE_CHOICE, q.type)
        vm.useFiftyFifty()
        val hidden = vm.state.value.hiddenOptions
        assertEquals(DailyChallenge.fiftyFiftyRemoves(q, today, 0), hidden)
        assertFalse(vm.state.value.fiftyFiftyAvailable)
        assertEquals(0, saved.last().fiftyFiftyIndex)

        // A hidden option can't be picked.
        vm.submitMultipleChoiceAnswer(hidden.first())
        assertEquals(AnswerStatus.NONE, vm.state.value.answerStatus)

        vm.answer(correct = true)
        vm.advanceToNextQuestion()
        vm.answer(correct = true)
        vm.advanceToNextQuestion()
        assertTrue(vm.state.value.hiddenOptions.isEmpty())
        vm.useFiftyFifty()
        assertTrue(vm.state.value.hiddenOptions.isEmpty())
    }

    @Test
    fun `fifty-fifty does nothing on a type-in or in a classic game`() {
        val vm = viewModel()
        vm.startDaily()
        vm.answer(correct = true)
        vm.advanceToNextQuestion()
        vm.useFiftyFifty()
        assertTrue(vm.state.value.fiftyFiftyAvailable)

        vm.goHome()
        vm.startGame()
        vm.useFiftyFifty()
        assertTrue(vm.state.value.hiddenOptions.isEmpty())
    }

    @Test
    fun `a typo earns half points in the daily but not in a classic game`() {
        val vm = viewModel()
        vm.startDaily()
        // Advance to the first type-in whose answer can be "close".
        val longAnswer = Regex("""^[A-Za-z ]{6,}$""")
        while (!(vm.current().type == QuestionType.TYPE_IN && vm.current().correctAnswer.matches(longAnswer))) {
            vm.answer(correct = true)
            vm.advanceToNextQuestion()
            assertEquals("no long type-in answer on the test day", GamePhase.PLAYING, vm.state.value.phase)
        }
        val answer = vm.current().correctAnswer
        assertTrue("streak should be running before the typo", vm.state.value.streak > 0)
        vm.updateTypeInText(answer.dropLast(1))
        vm.submitTypeInAnswer()
        with(vm.state.value) {
            assertEquals(AnswerStatus.CLOSE, answerStatus)
            assertEquals(Scoring.CLOSE_POINTS, lastPointsAwarded)
            assertEquals(0, streak)
            assertEquals(Outcome.CLOSE, outcomes.last())
        }
    }

    @Test
    fun `classic games never score close`() {
        val vm = viewModel()
        vm.startGame()
        while (vm.current().type != QuestionType.TYPE_IN) {
            vm.answer(correct = true)
            vm.advanceToNextQuestion()
        }
        vm.updateTypeInText(vm.current().correctAnswer + "x")
        vm.submitTypeInAnswer()
        assertTrue(vm.state.value.answerStatus != AnswerStatus.CLOSE)
        coVerify(exactly = 0) { dailyRepo.save(any()) }
    }
}
