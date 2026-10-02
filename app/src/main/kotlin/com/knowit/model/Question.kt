package com.knowit.model

import androidx.annotation.StringRes
import com.knowit.R

enum class QuestionType {
    MULTIPLE_CHOICE,
    TYPE_IN
}

enum class Category(@StringRes val labelRes: Int, val emoji: String) {
    SCIENCE(R.string.category_science, "🔬"),
    HISTORY(R.string.category_history, "📜"),
    GEOGRAPHY(R.string.category_geography, "🌍"),
    POP_CULTURE(R.string.category_pop_culture, "🎬"),
    TECH(R.string.category_tech, "💻")
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
