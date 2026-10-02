package com.knowit.model

object Scoring {
    const val POINTS_PER_CORRECT = 10
    const val STREAK_BONUS = 5

    /** Points for an answer, given the streak *before* this answer. */
    fun pointsFor(isCorrect: Boolean, streakBefore: Int): Int = when {
        !isCorrect -> 0
        streakBefore >= 1 -> POINTS_PER_CORRECT + STREAK_BONUS
        else -> POINTS_PER_CORRECT
    }

    /** Best possible score: the first answer earns base points, every later one also gets the bonus. */
    fun maxScore(questionCount: Int): Int =
        if (questionCount <= 0) 0
        else POINTS_PER_CORRECT * questionCount + STREAK_BONUS * (questionCount - 1)
}
