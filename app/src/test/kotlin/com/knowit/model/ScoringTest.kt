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
}
