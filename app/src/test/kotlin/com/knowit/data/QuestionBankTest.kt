package com.knowit.data

import com.knowit.model.Category
import com.knowit.model.QuestionType
import com.knowit.viewmodel.GameViewModel
import org.junit.Assert.assertEquals
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
}
