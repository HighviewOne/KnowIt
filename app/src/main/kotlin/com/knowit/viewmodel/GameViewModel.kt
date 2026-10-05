package com.knowit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knowit.data.HighScoreRepository
import com.knowit.data.questionBank
import com.knowit.model.Question
import com.knowit.model.QuestionType
import com.knowit.model.Scoring
import com.knowit.model.accepts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class GamePhase {
    HOME,
    PLAYING,
    GAME_OVER
}

enum class AnswerStatus {
    NONE,
    CORRECT,
    WRONG
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
    val isNewHighScore: Boolean = false
)

class GameViewModel(
    private val highScoreRepository: HighScoreRepository,
    private val questionSource: List<Question> = questionBank,
    private val random: Random = Random.Default,
    private val questionsPerGame: Int = QUESTIONS_PER_GAME
) : ViewModel() {

    companion object {
        /** Each game draws this many questions from the bank, half of each format. */
        const val QUESTIONS_PER_GAME = 20
    }

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val savedScore = highScoreRepository.getHighScore()
            _state.update { it.copy(highScore = savedScore) }
        }
    }

    fun startGame() {
        val questions = shuffledQuestions()
        val shuffledOptions = shuffledOptionsFor(questions.first())

        _state.update { current ->
            current.copy(
                phase = GamePhase.PLAYING,
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

    private fun shuffledOptionsFor(question: Question): List<String> =
        if (question.type == QuestionType.MULTIPLE_CHOICE) question.options.shuffled(random) else emptyList()

    fun submitMultipleChoiceAnswer(option: String) {
        val question = currentUnansweredQuestion() ?: return
        recordAnswer(isCorrect = option == question.correctAnswer, selectedOption = option)
    }

    fun submitTypeInAnswer() {
        if (_state.value.typeInText.isBlank()) return
        val question = currentUnansweredQuestion() ?: return
        recordAnswer(isCorrect = question.accepts(_state.value.typeInText), selectedOption = null)
    }

    private fun currentUnansweredQuestion(): Question? {
        val state = _state.value
        if (state.phase != GamePhase.PLAYING || state.answerStatus != AnswerStatus.NONE) return null
        return state.questions.getOrNull(state.currentQuestionIndex)
    }

    private fun recordAnswer(isCorrect: Boolean, selectedOption: String?) {
        _state.update {
            val points = Scoring.pointsFor(isCorrect, streakBefore = it.streak)
            it.copy(
                answerStatus = if (isCorrect) AnswerStatus.CORRECT else AnswerStatus.WRONG,
                score = it.score + points,
                streak = if (isCorrect) it.streak + 1 else 0,
                correctCount = if (isCorrect) it.correctCount + 1 else it.correctCount,
                selectedOption = selectedOption,
                lastPointsAwarded = points
            )
        }
    }

    fun advanceToNextQuestion() {
        val state = _state.value
        if (state.phase != GamePhase.PLAYING) return
        if (state.answerStatus == AnswerStatus.NONE) return
        val nextIndex = state.currentQuestionIndex + 1

        if (nextIndex >= state.questions.size) {
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

        val shuffledOptions = shuffledOptionsFor(state.questions[nextIndex])

        _state.update {
            it.copy(
                currentQuestionIndex = nextIndex,
                answerStatus = AnswerStatus.NONE,
                shuffledOptions = shuffledOptions,
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
    }
}
