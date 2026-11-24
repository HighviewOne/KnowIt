package com.knowit.model

/** How one answer turned out. [CLOSE] is a type-in one typo away from right (daily challenge only). */
enum class Outcome {
    CORRECT,
    CLOSE,
    WRONG
}

object Scoring {
    const val POINTS_PER_CORRECT = 10
    const val STREAK_BONUS = 5
    const val CLOSE_POINTS = 5

    /** Points for an answer, given the streak *before* this answer. */
    fun pointsFor(isCorrect: Boolean, streakBefore: Int): Int =
        pointsFor(if (isCorrect) Outcome.CORRECT else Outcome.WRONG, streakBefore)

    /** Points for an answer, given the streak *before* this answer. A close answer earns half and no bonus. */
    fun pointsFor(outcome: Outcome, streakBefore: Int): Int = when (outcome) {
        Outcome.WRONG -> 0
        Outcome.CLOSE -> CLOSE_POINTS
        Outcome.CORRECT -> if (streakBefore >= 1) POINTS_PER_CORRECT + STREAK_BONUS else POINTS_PER_CORRECT
    }

    /** Streak after an answer: only a correct answer extends it; close and wrong both reset it. */
    fun streakAfter(outcome: Outcome, streakBefore: Int): Int =
        if (outcome == Outcome.CORRECT) streakBefore + 1 else 0

    /** Total score and final streak for a run of answers, in order. */
    fun tally(outcomes: List<Outcome>): Pair<Int, Int> =
        outcomes.fold(0 to 0) { (score, streak), outcome ->
            (score + pointsFor(outcome, streak)) to streakAfter(outcome, streak)
        }

    /** Best possible score: the first answer earns base points, every later one also gets the bonus. */
    fun maxScore(questionCount: Int): Int =
        if (questionCount <= 0) 0
        else POINTS_PER_CORRECT * questionCount + STREAK_BONUS * (questionCount - 1)
}
