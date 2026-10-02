package com.knowit.model

import com.knowit.data.questionBank
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AnswerMatchingTest {

    private fun typeIn(correct: String, vararg accepted: String) = Question(
        id = 0,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "?",
        correctAnswer = correct,
        acceptedAnswers = accepted.toList()
    )

    @Test
    fun `normalize ignores case, punctuation, whitespace and a leading article`() {
        assertEquals("robert downey jr", normalizeAnswer("  Robert   Downey, Jr. "))
        assertEquals("nile", normalizeAnswer("The Nile!"))
        assertEquals("vatican city", normalizeAnswer("vatican-city"))
    }

    @Test
    fun `normalize drops thousands separators`() {
        assertEquals("300000", normalizeAnswer("300,000"))
        assertEquals("299792", normalizeAnswer("299.792"))
    }

    @Test
    fun `accepts variants of the correct answer without listing them`() {
        val q = typeIn("Robert Downey Jr", "rdj")
        assertTrue(q.accepts("robert downey jr."))
        assertTrue(q.accepts("ROBERT DOWNEY, JR"))
        assertTrue(q.accepts("RDJ"))
        assertFalse(q.accepts("Robert Downey Sr"))
    }

    @Test
    fun `accepts leading article and punctuation`() {
        assertTrue(typeIn("Nile").accepts("the Nile."))
        assertTrue(typeIn("300000").accepts("300,000"))
    }

    @Test
    fun `rejects blank and punctuation-only input`() {
        val q = typeIn("Mars")
        assertFalse(q.accepts(""))
        assertFalse(q.accepts("  ...  "))
    }

    @Test
    fun `every bank question accepts its own correct answer`() {
        questionBank.filter { it.type == QuestionType.TYPE_IN }.forEach { q ->
            assertTrue(q.questionText, q.accepts(q.correctAnswer))
            q.acceptedAnswers.forEach { assertTrue("${q.questionText} / $it", q.accepts(it)) }
        }
    }
}
