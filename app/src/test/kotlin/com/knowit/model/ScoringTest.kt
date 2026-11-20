package com.knowit.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ScoringTest {

    @Test
    fun `points depend on correctness and prior streak`() {
        assertEquals(0, Scoring.pointsFor(isCorrect = false, streakBefore = 5))
        assertEquals(10, Scoring.pointsFor(isCorrect = true, streakBefore = 0))
        assertEquals(15, Scoring.pointsFor(isCorrect = true, streakBefore = 1))
    }

    @Test
    fun `max score`() {
        assertEquals(0, Scoring.maxScore(0))
        assertEquals(10, Scoring.maxScore(1))
        assertEquals(295, Scoring.maxScore(20))
    }

    @Test
    fun `close answer earns half points, no bonus, and resets the streak`() {
        assertEquals(5, Scoring.pointsFor(Outcome.CLOSE, streakBefore = 0))
        assertEquals(5, Scoring.pointsFor(Outcome.CLOSE, streakBefore = 4))
        assertEquals(0, Scoring.streakAfter(Outcome.CLOSE, streakBefore = 4))
        assertEquals(5, Scoring.streakAfter(Outcome.CORRECT, streakBefore = 4))
    }

    @Test
    fun `tally replays a run`() {
        val run = listOf(Outcome.CORRECT, Outcome.CORRECT, Outcome.CLOSE, Outcome.CORRECT, Outcome.WRONG)
        // 10 + 15 + 5 + 10 + 0
        assertEquals(40 to 0, Scoring.tally(run))
        assertEquals(0 to 0, Scoring.tally(emptyList()))
        assertEquals(Scoring.maxScore(10) to 10, Scoring.tally(List(10) { Outcome.CORRECT }))
    }
}
