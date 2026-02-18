package com.knowit.viewmodel

import androidx.lifecycle.ViewModel
import com.knowit.data.questionBank
import com.knowit.model.Question
import com.knowit.model.QuestionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
    val lastPointsAwarded: Int = 0
)

class GameViewModel : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    fun startGame() {
        val questions = questionBank
        val firstQuestion = questions.first()
        val shuffledOptions = if (firstQuestion.type == QuestionType.MULTIPLE_CHOICE) {
            firstQuestion.options.shuffled()
        } else {
            emptyList()
        }

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
                lastPointsAwarded = 0
            )
        }
    }

    fun submitMultipleChoiceAnswer(option: String) {
        val state = _state.value
        if (state.answerStatus != AnswerStatus.NONE) return

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
        val nextIndex = state.currentQuestionIndex + 1

        if (nextIndex >= state.questions.size) {
            val newHighScore = maxOf(state.highScore, state.score)
            _state.update {
                it.copy(
                    phase = GamePhase.GAME_OVER,
                    highScore = newHighScore
                )
            }
            return
        }

        val nextQuestion = state.questions[nextIndex]
        val shuffledOptions = if (nextQuestion.type == QuestionType.MULTIPLE_CHOICE) {
            nextQuestion.options.shuffled()
        } else {
            emptyList()
        }

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
