package com.knowit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knowit.data.DailyRepository
import com.knowit.data.DailySave
import com.knowit.data.HighScoreRepository
import com.knowit.data.questionBank
import com.knowit.model.DailyChallenge
import com.knowit.model.Outcome
import com.knowit.model.Question
import com.knowit.model.QuestionType
import com.knowit.model.Scoring
import com.knowit.model.accepts
import com.knowit.model.isNearMiss
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

enum class GamePhase {
    HOME,
    PLAYING,
    GAME_OVER
}

enum class GameMode {
    /** 20 random questions, counts toward the high score. */
    CLASSIC,
    /** Today's 10 questions, one run per day, with its own streak. */
    DAILY
}

enum class AnswerStatus {
    NONE,
    CORRECT,
    /** Type-in one typo away from right: half points (daily challenge only). */
    CLOSE,
    WRONG
}

/** Today's daily challenge as the home screen shows it. */
data class DailyStatus(
    val number: Int = 1,
    val answered: Int = 0,
    val total: Int = DailyChallenge.QUESTION_COUNT,
    val score: Int = 0,
    val streak: Int = 0,
    val bestStreak: Int = 0
) {
    val isDone: Boolean get() = answered >= total
    val isInProgress: Boolean get() = answered in 1 until total
}

data class GameState(
    val phase: GamePhase = GamePhase.HOME,
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val streak: Int = 0,
    val answerStatus: AnswerStatus = AnswerStatus.NONE,
    val shuffledOptions: List<String> = emptyList(),
    val typeInText: String = "",
    val highScore: Int = 0,
    val correctCount: Int = 0,
    val selectedOption: String? = null,
    val lastPointsAwarded: Int = 0,
    val isNewHighScore: Boolean = false,
    val mode: GameMode = GameMode.CLASSIC,
    /** Answers so far this run, in order. */
    val outcomes: List<Outcome> = emptyList(),
    /** Daily number of the run being played (daily mode). */
    val dailyNumber: Int = 0,
    /** True while the daily run's single 50/50 is unused. */
    val fiftyFiftyAvailable: Boolean = false,
    /** Wrong options the 50/50 removed from the current question. */
    val hiddenOptions: Set<String> = emptySet(),
    val daily: DailyStatus = DailyStatus()
)

class GameViewModel(
    private val highScoreRepository: HighScoreRepository,
    private val dailyRepository: DailyRepository,
    private val questionSource: List<Question> = questionBank,
    private val random: Random = Random.Default,
    private val questionsPerGame: Int = QUESTIONS_PER_GAME,
    private val today: () -> LocalDate = { LocalDate.now() }
) : ViewModel() {

    companion object {
        /** Each game draws this many questions from the bank, half of each format. */
        const val QUESTIONS_PER_GAME = 20
    }

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    /** In-memory copy of the saved daily state; null until loaded. */
    private var dailySave: DailySave? = null

    /** Date of the daily run being played (a run started before midnight keeps its date). */
    private var dailyDay: LocalDate = LocalDate.MIN

    /** Question index the 50/50 was used on this daily run, or -1. */
    private var fiftyFiftyIndex = -1

    private var startingDaily = false

    init {
        viewModelScope.launch {
            val savedScore = highScoreRepository.getHighScore()
            _state.update { it.copy(highScore = savedScore) }
        }
        viewModelScope.launch {
            if (dailySave == null) dailySave = dailyRepository.load()
            refreshDaily()
        }
    }

    /** Recomputes the home screen's daily status for today's date (call when the app resumes). */
    fun refreshDaily() {
        val save = dailySave ?: return
        val date = today()
        val outcomes = save.outcomesFor(date.toEpochDay())
        val status = DailyStatus(
            number = DailyChallenge.numberFor(date),
            answered = outcomes.size,
            total = DailyChallenge.questionsFor(date, questionSource).size,
            score = Scoring.tally(outcomes).first,
            streak = save.currentStreak(date.toEpochDay()),
            bestStreak = save.bestStreak
        )
        _state.update { it.copy(daily = status) }
    }

    fun startGame() {
        val questions = shuffledQuestions()
        val shuffledOptions = shuffledOptionsFor(questions.first(), 0, GameMode.CLASSIC)

        _state.update { current ->
            current.copy(
                phase = GamePhase.PLAYING,
                mode = GameMode.CLASSIC,
                outcomes = emptyList(),
                fiftyFiftyAvailable = false,
                hiddenOptions = emptySet(),
                questions = questions,
                currentQuestionIndex = 0,
                score = 0,
                streak = 0,
                answerStatus = AnswerStatus.NONE,
                shuffledOptions = shuffledOptions,
                typeInText = "",
                correctCount = 0,
                selectedOption = null,
                lastPointsAwarded = 0,
                isNewHighScore = false
            )
        }
    }

    /**
     * Draws [questionsPerGame] random questions (half of each format), then interleaves
     * them so formats alternate.
     */
    private fun shuffledQuestions(): List<Question> {
        val perType = questionsPerGame / 2
        val (allMultipleChoice, allTypeIn) = questionSource
            .shuffled(random)
            .partition { it.type == QuestionType.MULTIPLE_CHOICE }
        val multipleChoice = allMultipleChoice.take(perType)
        val typeIn = allTypeIn.take(perType)
        return (0 until maxOf(multipleChoice.size, typeIn.size)).flatMap { i ->
            listOfNotNull(multipleChoice.getOrNull(i), typeIn.getOrNull(i))
        }
    }

    /**
     * Starts or resumes today's daily challenge. A finished challenge goes straight to its
     * result; an unfinished one picks up at the first unanswered question.
     */
    fun startDaily() {
        if (_state.value.phase != GamePhase.HOME || startingDaily) return
        startingDaily = true
        viewModelScope.launch {
            try {
                val save = dailySave ?: dailyRepository.load().also { dailySave = it }
                val date = today()
                val questions = DailyChallenge.questionsFor(date, questionSource)
                if (questions.isEmpty()) return@launch
                val outcomes = save.outcomesFor(date.toEpochDay()).take(questions.size)
                val (score, streak) = Scoring.tally(outcomes)
                dailyDay = date
                fiftyFiftyIndex = save.fiftyFiftyIndexFor(date.toEpochDay())
                val index = outcomes.size.coerceAtMost(questions.size - 1)
                val done = outcomes.size >= questions.size
                _state.update {
                    it.copy(
                        phase = if (done) GamePhase.GAME_OVER else GamePhase.PLAYING,
                        mode = GameMode.DAILY,
                        dailyNumber = DailyChallenge.numberFor(date),
                        questions = questions,
                        currentQuestionIndex = index,
                        outcomes = outcomes,
                        score = score,
                        streak = streak,
                        correctCount = outcomes.count { o -> o == Outcome.CORRECT },
                        answerStatus = AnswerStatus.NONE,
                        shuffledOptions = shuffledOptionsFor(questions[index], index, GameMode.DAILY),
                        hiddenOptions = hiddenOptionsFor(questions[index], index),
                        fiftyFiftyAvailable = fiftyFiftyIndex < 0,
                        typeInText = "",
                        selectedOption = null,
                        lastPointsAwarded = 0,
                        isNewHighScore = false
                    )
                }
            } finally {
                startingDaily = false
            }
        }
    }

    private fun shuffledOptionsFor(question: Question, index: Int, mode: GameMode): List<String> = when {
        question.type != QuestionType.MULTIPLE_CHOICE -> emptyList()
        mode == GameMode.DAILY -> DailyChallenge.optionOrder(question, dailyDay, index)
        else -> question.options.shuffled(random)
    }

    private fun hiddenOptionsFor(question: Question, index: Int): Set<String> =
        if (fiftyFiftyIndex == index && question.type == QuestionType.MULTIPLE_CHOICE)
            DailyChallenge.fiftyFiftyRemoves(question, dailyDay, index)
        else emptySet()

    /** Removes two wrong options from the current daily multiple-choice question. Once per daily run. */
    fun useFiftyFifty() {
        val state = _state.value
        if (state.mode != GameMode.DAILY || !state.fiftyFiftyAvailable) return
        val question = currentUnansweredQuestion() ?: return
        if (question.type != QuestionType.MULTIPLE_CHOICE) return
        fiftyFiftyIndex = state.currentQuestionIndex
        _state.update {
            it.copy(
                fiftyFiftyAvailable = false,
                hiddenOptions = hiddenOptionsFor(question, state.currentQuestionIndex)
            )
        }
        persistDaily(state.outcomes)
    }

    fun submitMultipleChoiceAnswer(option: String) {
        val question = currentUnansweredQuestion() ?: return
        if (option in _state.value.hiddenOptions) return
        recordAnswer(
            outcome = if (option == question.correctAnswer) Outcome.CORRECT else Outcome.WRONG,
            selectedOption = option
        )
    }

    fun submitTypeInAnswer() {
        val text = _state.value.typeInText
        if (text.isBlank()) return
        val question = currentUnansweredQuestion() ?: return
        val outcome = when {
            question.accepts(text) -> Outcome.CORRECT
            _state.value.mode == GameMode.DAILY && question.isNearMiss(text) -> Outcome.CLOSE
            else -> Outcome.WRONG
        }
        recordAnswer(outcome = outcome, selectedOption = null)
    }

    private fun currentUnansweredQuestion(): Question? {
        val state = _state.value
        if (state.phase != GamePhase.PLAYING || state.answerStatus != AnswerStatus.NONE) return null
        return state.questions.getOrNull(state.currentQuestionIndex)
    }

    private fun recordAnswer(outcome: Outcome, selectedOption: String?) {
        _state.update {
            val points = Scoring.pointsFor(outcome, streakBefore = it.streak)
            it.copy(
                answerStatus = when (outcome) {
                    Outcome.CORRECT -> AnswerStatus.CORRECT
                    Outcome.CLOSE -> AnswerStatus.CLOSE
                    Outcome.WRONG -> AnswerStatus.WRONG
                },
                score = it.score + points,
                streak = Scoring.streakAfter(outcome, it.streak),
                correctCount = if (outcome == Outcome.CORRECT) it.correctCount + 1 else it.correctCount,
                outcomes = it.outcomes + outcome,
                selectedOption = selectedOption,
                lastPointsAwarded = points
            )
        }
        if (_state.value.mode == GameMode.DAILY) persistDaily(_state.value.outcomes)
    }

    /** Saves the daily run after every answer (and the 50/50), so it survives the app closing. */
    private fun persistDaily(outcomes: List<Outcome>) {
        val epochDay = dailyDay.toEpochDay()
        var save = (dailySave ?: DailySave()).copy(day = epochDay, outcomes = outcomes, fiftyFiftyIndex = fiftyFiftyIndex)
        // The run counts as finished at its last answer, even if the player leaves before tapping Next.
        if (outcomes.size >= _state.value.questions.size) save = save.completed(epochDay)
        dailySave = save
        viewModelScope.launch { dailyRepository.save(save) }
        refreshDaily()
    }

    fun advanceToNextQuestion() {
        val state = _state.value
        if (state.phase != GamePhase.PLAYING) return
        if (state.answerStatus == AnswerStatus.NONE) return
        val nextIndex = state.currentQuestionIndex + 1

        if (nextIndex >= state.questions.size) {
            if (state.mode == GameMode.DAILY) {
                // Already saved at the last answer; the daily never touches the high score.
                _state.update { it.copy(phase = GamePhase.GAME_OVER) }
                return
            }
            val isNewHighScore = state.score > state.highScore
            val newHighScore = maxOf(state.highScore, state.score)

            // Persist high score
            viewModelScope.launch {
                highScoreRepository.saveHighScore(newHighScore)
            }

            _state.update {
                it.copy(
                    phase = GamePhase.GAME_OVER,
                    highScore = newHighScore,
                    isNewHighScore = isNewHighScore
                )
            }
            return
        }

        val nextQuestion = state.questions[nextIndex]

        _state.update {
            it.copy(
                currentQuestionIndex = nextIndex,
                answerStatus = AnswerStatus.NONE,
                shuffledOptions = shuffledOptionsFor(nextQuestion, nextIndex, state.mode),
                hiddenOptions = hiddenOptionsFor(nextQuestion, nextIndex),
                typeInText = "",
                selectedOption = null,
                lastPointsAwarded = 0
            )
        }
    }

    fun updateTypeInText(text: String) {
        _state.update { it.copy(typeInText = text) }
    }

    fun goHome() {
        _state.update {
            it.copy(
                phase = GamePhase.HOME,
                answerStatus = AnswerStatus.NONE,
                typeInText = "",
                selectedOption = null
            )
        }
        refreshDaily()
    }
}
