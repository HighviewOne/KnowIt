package com.knowit.model

enum class QuestionType {
    MULTIPLE_CHOICE,
    TYPE_IN
}

enum class Category(val displayName: String, val emoji: String) {
    SCIENCE("Science", "🔬"),
    HISTORY("History", "📜"),
    GEOGRAPHY("Geography", "🌍"),
    POP_CULTURE("Pop Culture", "🎬"),
    TECH("Tech", "💻")
}

data class Question(
    val id: Int,
    val type: QuestionType,
    val category: Category,
    val questionText: String,
    val correctAnswer: String,
    val options: List<String> = emptyList(),
    val acceptedAnswers: List<String> = emptyList()
)
