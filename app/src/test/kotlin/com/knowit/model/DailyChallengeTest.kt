package com.knowit.model

import com.knowit.data.questionBank
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class DailyChallengeTest {

    private val day = LocalDate.of(2026, 12, 1)

    @Test
    fun `same date gives the same questions, whatever order the bank is in`() {
        val a = DailyChallenge.questionsFor(day, questionBank)
        val b = DailyChallenge.questionsFor(day, questionBank.reversed())
        assertEquals(a.map { it.id }, b.map { it.id })
    }

    @Test
    fun `consecutive days differ`() {
        val sets = (0L until 30L).map { offset ->
            DailyChallenge.questionsFor(day.plusDays(offset), questionBank).map { it.id }
        }
        assertEquals(sets.size, sets.toSet().size)
    }

    @Test
    fun `a year of challenges follows the plan`() {
        repeat(365) { offset ->
            val questions = DailyChallenge.questionsFor(day.plusDays(offset.toLong()), questionBank)
            assertEquals(DailyChallenge.QUESTION_COUNT, questions.size)
            assertEquals(questions.size, questions.map { it.id }.toSet().size)
            questions.forEachIndexed { i, q ->
                val type = if (i % 2 == 0) QuestionType.MULTIPLE_CHOICE else QuestionType.TYPE_IN
                assertEquals(type, q.type)
                assertEquals(DailyChallenge.DIFFICULTY_PLAN[i], q.difficulty)
            }
            assertTrue(questions.groupingBy { it.category }.eachCount().values.all { it <= 2 })
        }
    }

    @Test
    fun `a small bank still fills what it can without repeats`() {
        val small = questionBank.filter { it.difficulty == Difficulty.EASY }.take(6)
        val questions = DailyChallenge.questionsFor(day, small)
        assertEquals(6, questions.size)
        assertEquals(6, questions.map { it.id }.toSet().size)
    }

    @Test
    fun `numbers count from the first day`() {
        assertEquals(1, DailyChallenge.numberFor(DailyChallenge.FIRST_DAY))
        assertEquals(12, DailyChallenge.numberFor(DailyChallenge.FIRST_DAY.plusDays(11)))
        assertEquals(1, DailyChallenge.numberFor(DailyChallenge.FIRST_DAY.minusDays(30)))
    }

    @Test
    fun `option order and 50-50 are fixed per day and question`() {
        val q = questionBank.first { it.type == QuestionType.MULTIPLE_CHOICE }
        assertEquals(DailyChallenge.optionOrder(q, day, 3), DailyChallenge.optionOrder(q, day, 3))
        assertEquals(q.options.toSet(), DailyChallenge.optionOrder(q, day, 3).toSet())

        val removed = DailyChallenge.fiftyFiftyRemoves(q, day, 3)
        assertEquals(removed, DailyChallenge.fiftyFiftyRemoves(q, day, 3))
        assertEquals(2, removed.size)
        assertTrue(q.correctAnswer !in removed)
        assertTrue(q.options.containsAll(removed))
    }

    @Test
    fun `grid has one square per answer`() {
        assertEquals("🟩🟨🟥", DailyChallenge.grid(listOf(Outcome.CORRECT, Outcome.CLOSE, Outcome.WRONG)))
        assertNotEquals("", DailyChallenge.grid(List(10) { Outcome.CORRECT }))
    }
}
