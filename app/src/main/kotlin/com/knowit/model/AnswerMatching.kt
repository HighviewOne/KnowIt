package com.knowit.model

private val LEADING_ARTICLE = Regex("""^(the|a|an)\s+""")
private val NON_ALPHANUMERIC = Regex("""[^\p{L}\p{N}]+""")
private val DIGIT_GROUP_SEPARATOR = Regex("""(?<=\d)[,.](?=\d{3}\b)""")

/**
 * Canonical form for comparing typed answers: case-insensitive, ignores punctuation,
 * extra whitespace, a leading article, and thousands separators ("300,000" == "300000").
 */
fun normalizeAnswer(text: String): String =
    text.lowercase()
        .replace(DIGIT_GROUP_SEPARATOR, "")
        .replace(NON_ALPHANUMERIC, " ")
        .trim()
        .replace(LEADING_ARTICLE, "")

/** True if [input] matches the correct answer or any accepted alternative. */
fun Question.accepts(input: String): Boolean {
    val normalized = normalizeAnswer(input)
    if (normalized.isEmpty()) return false
    return (acceptedAnswers + correctAnswer).any { normalizeAnswer(it) == normalized }
}
