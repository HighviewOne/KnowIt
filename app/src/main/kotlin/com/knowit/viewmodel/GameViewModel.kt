package com.knowit.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knowit.data.HighScoreRepository
import com.knowit.data.questionBank
import com.knowit.model.Question
import com.knowit.model.QuestionType
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
    private val savedStateHandle: SavedStateHandle,
    private val highScoreRepository: HighScoreRepository,
    private val questionSource: List<Question> = questionBank,
    private val random: Random = Random.Default
) : ViewModel() {

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

    /** Shuffles each question type separately, then interleaves them so formats still alternate. */
    private fun shuffledQuestions(): List<Question> {
        val (multipleChoice, typeIn) = questionSource
            .shuffled(random)
            .partition { it.type == QuestionType.MULTIPLE_CHOICE }
        return (0 until maxOf(multipleChoice.size, typeIn.size)).flatMap { i ->
            listOfNotNull(multipleChoice.getOrNull(i), typeIn.getOrNull(i))
        }
    }

    private fun shuffledOptionsFor(question: Question): List<String> =
        if (question.type == QuestionType.MULTIPLE_CHOICE) question.options.shuffled(random) else emptyList()

    fun submitMultipleChoiceAnswer(option: String) {
        val state = _state.value
        if (state.answerStatus != AnswerStatus.NONE) return
        if (state.currentQuestionIndex >= state.questions.size) return

        val currentQuestion = state.questions[state.currentQuestionIndex]
        val isCorrect = option == currentQuestion.correctAnswer

        val basePoints = if (isCorrect) 10 else 0
        val bonusPoints = if (isCorrect && state.streak >= 1) 5 else 0
        val pointsAwarded = basePoints + bonusPoints

        val newStreak = if (isCorrect) state.streak + 1 else 0
        val newScore = state.score + pointsAwarded
        val newCorrectCount = if (isCorrect) state.correctCount + 1 else state.correctCount

        _state.update {
            it.copy(
                answerStatus = if (isCorrect) AnswerStatus.CORRECT else AnswerStatus.WRONG,
                score = newScore,
                streak = newStreak,
                correctCount = newCorrectCount,
                selectedOption = option,
                lastPointsAwarded = pointsAwarded
            )
        }
    }

    fun submitTypeInAnswer() {
        val state = _state.value
        if (state.answerStatus != AnswerStatus.NONE) return
        if (state.typeInText.isBlank()) return
        if (state.currentQuestionIndex >= state.questions.size) return

        val currentQuestion = state.questions[state.currentQuestionIndex]
        val userInput = state.typeInText.trim()

        val isCorrect = userInput.equals(currentQuestion.correctAnswer, ignoreCase = true) ||
            currentQuestion.acceptedAnswers.any { accepted ->
                userInput.equals(accepted, ignoreCase = true)
            }

        val basePoints = if (isCorrect) 10 else 0
        val bonusPoints = if (isCorrect && state.streak >= 1) 5 else 0
        val pointsAwarded = basePoints + bonusPoints

        val newStreak = if (isCorrect) state.streak + 1 else 0
        val newScore = state.score + pointsAwarded
        val newCorrectCount = if (isCorrect) state.correctCount + 1 else state.correctCount

        _state.update {
            it.copy(
                answerStatus = if (isCorrect) AnswerStatus.CORRECT else AnswerStatus.WRONG,
                score = newScore,
                streak = newStreak,
                correctCount = newCorrectCount,
                lastPointsAwarded = pointsAwarded
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
