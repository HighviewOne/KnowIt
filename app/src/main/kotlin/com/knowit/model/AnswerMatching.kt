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

/** Shortest correct answers that can earn half credit; shorter ones are too easy to confuse ("Iran"/"Iraq"). */
private const val MIN_NEAR_MISS_LENGTH = 6

/**
 * True if [input] is one typo away from an accepted answer: one letter added, dropped, changed,
 * or two neighbours swapped. To avoid rewarding a different real answer, it only applies to
 * answers of [MIN_NEAR_MISS_LENGTH]+ characters with no digits (so 1944 is never "close" to
 * 1945), and the first letter must match ("Gambia" is not close to "Zambia").
 */
fun Question.isNearMiss(input: String): Boolean {
    val typed = normalizeAnswer(input)
    if (typed.isEmpty() || accepts(input)) return false
    return (acceptedAnswers + correctAnswer).map(::normalizeAnswer).any { answer ->
        answer.length >= MIN_NEAR_MISS_LENGTH &&
            answer.none(Char::isDigit) &&
            typed.first() == answer.first() &&
            typoDistance(typed, answer) == 1
    }
}

/** Optimal string alignment distance: Levenshtein plus adjacent transpositions. */
internal fun typoDistance(a: String, b: String): Int {
    val d = Array(a.length + 1) { i -> IntArray(b.length + 1) { j -> if (i == 0) j else if (j == 0) i else 0 } }
    for (i in 1..a.length) {
        for (j in 1..b.length) {
            val cost = if (a[i - 1] == b[j - 1]) 0 else 1
            d[i][j] = minOf(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost)
            if (i > 1 && j > 1 && a[i - 1] == b[j - 2] && a[i - 2] == b[j - 1]) {
                d[i][j] = minOf(d[i][j], d[i - 2][j - 2] + 1)
            }
        }
    }
    return d[a.length][b.length]
}
