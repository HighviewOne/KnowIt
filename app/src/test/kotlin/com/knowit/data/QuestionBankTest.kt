package com.knowit.data

import com.knowit.model.Category
import com.knowit.model.Difficulty
import com.knowit.model.QuestionType
import com.knowit.model.normalizeAnswer
import com.knowit.viewmodel.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QuestionBankTest {

    @Test
    fun `ids are unique`() {
        assertEquals(questionBank.size, questionBank.map { it.id }.toSet().size)
    }

    @Test
    fun `question texts are unique`() {
        assertEquals(questionBank.size, questionBank.map { it.questionText.lowercase() }.toSet().size)
    }

    @Test
    fun `multiple choice questions have four distinct options including the answer`() {
        questionBank.filter { it.type == QuestionType.MULTIPLE_CHOICE }.forEach { q ->
            assertEquals(q.questionText, 4, q.options.toSet().size)
            assertTrue(q.questionText, q.correctAnswer in q.options)
        }
    }

    @Test
    fun `type-in questions have no options`() {
        questionBank.filter { it.type == QuestionType.TYPE_IN }.forEach { q ->
            assertTrue(q.questionText, q.options.isEmpty())
        }
    }

    @Test
    fun `bank can fill a game with each format`() {
        val perType = GameViewModel.QUESTIONS_PER_GAME / 2
        QuestionType.entries.forEach { type ->
            assertTrue("$type", questionBank.count { it.type == type } >= perType)
        }
    }

    @Test
    fun `every category has both formats`() {
        Category.entries.forEach { category ->
            QuestionType.entries.forEach { type ->
                assertTrue("$category/$type", questionBank.any { it.category == category && it.type == type })
            }
        }
    }

    @Test
    fun `every category has at least five questions of each format`() {
        Category.entries.forEach { category ->
            QuestionType.entries.forEach { type ->
                val count = questionBank.count { it.category == category && it.type == type }
                assertTrue("$category/$type has $count", count >= 5)
            }
        }
    }

    @Test
    fun `every difficulty is used`() {
        Difficulty.entries.forEach { difficulty ->
            assertTrue("$difficulty", questionBank.any { it.difficulty == difficulty })
        }
    }

    @Test
    fun `answers survive normalization`() {
        questionBank.forEach { q ->
            (q.acceptedAnswers + q.correctAnswer).forEach { answer ->
                assertTrue("${q.id}: '$answer'", normalizeAnswer(answer).isNotEmpty())
            }
        }
    }

    @Test
    fun `accepted answers are not duplicates of each other or the correct answer`() {
        questionBank.forEach { q ->
            val all = (q.acceptedAnswers + q.correctAnswer).map(::normalizeAnswer)
            assertEquals("${q.id}: $all", all.size, all.toSet().size)
        }
    }

    @Test
    fun `multiple choice questions do not list accepted answers`() {
        questionBank.filter { it.type == QuestionType.MULTIPLE_CHOICE }.forEach { q ->
            assertFalse("${q.id}", q.acceptedAnswers.isNotEmpty())
        }
    }
}
