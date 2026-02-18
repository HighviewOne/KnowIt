package com.knowit.data

import com.knowit.model.Category
import com.knowit.model.Question
import com.knowit.model.QuestionType

val questionBank: List<Question> = listOf(
    // Pair 1: Science MC
    Question(
        id = 1,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "What is the chemical symbol for gold?",
        correctAnswer = "Au",
        options = listOf("Au", "Ag", "Fe", "Gd")
    ),
    // Pair 1: History TypeIn
    Question(
        id = 2,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "In what year did World War II end?",
        correctAnswer = "1945",
        acceptedAnswers = listOf("1945")
    ),
    // Pair 2: Geography MC
    Question(
        id = 3,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "What is the capital of France?",
        correctAnswer = "Paris",
        options = listOf("Paris", "London", "Berlin", "Madrid")
    ),
    // Pair 2: Pop Culture TypeIn
    Question(
        id = 4,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "Who played Iron Man in the Marvel Cinematic Universe?",
        correctAnswer = "Robert Downey Jr",
        acceptedAnswers = listOf("robert downey jr", "robert downey jr.", "rdj", "robert downey")
    ),
    // Pair 3: Tech MC
    Question(
        id = 5,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "What does CPU stand for?",
        correctAnswer = "Central Processing Unit",
        options = listOf("Central Processing Unit", "Computer Personal Unit", "Core Power Unit", "Central Program Utility")
    ),
    // Pair 3: Science TypeIn
    Question(
        id = 6,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "What planet is known as the Red Planet?",
        correctAnswer = "Mars",
        acceptedAnswers = listOf("mars")
    ),
    // Pair 4: History MC
    Question(
        id = 7,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "Who was the first President of the United States?",
        correctAnswer = "George Washington",
        options = listOf("George Washington", "Abraham Lincoln", "Thomas Jefferson", "John Adams")
    ),
    // Pair 4: Geography TypeIn
    Question(
        id = 8,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "What is the longest river in the world?",
        correctAnswer = "Nile",
        acceptedAnswers = listOf("nile", "nile river", "the nile")
    ),
    // Pair 5: Pop Culture MC
    Question(
        id = 9,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "Which movie won the first Academy Award for Best Picture?",
        correctAnswer = "Wings",
        options = listOf("Wings", "Sunrise", "The Jazz Singer", "7th Heaven")
    ),
    // Pair 5: Tech TypeIn
    Question(
        id = 10,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "What programming language was created by Guido van Rossum?",
        correctAnswer = "Python",
        acceptedAnswers = listOf("python")
    ),
    // Pair 6: Science MC
    Question(
        id = 11,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "How many bones are in the adult human body?",
        correctAnswer = "206",
        options = listOf("206", "198", "215", "223")
    ),
    // Pair 6: History TypeIn
    Question(
        id = 12,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "What ancient wonder was located in Alexandria, Egypt?",
        correctAnswer = "Lighthouse",
        acceptedAnswers = listOf("lighthouse", "lighthouse of alexandria", "the lighthouse of alexandria", "pharos")
    ),
    // Pair 7: Geography MC
    Question(
        id = 13,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "Which country has the most natural lakes?",
        correctAnswer = "Canada",
        options = listOf("Canada", "Russia", "USA", "Finland")
    ),
    // Pair 7: Pop Culture TypeIn
    Question(
        id = 14,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "How many strings does a standard guitar have?",
        correctAnswer = "6",
        acceptedAnswers = listOf("6", "six")
    ),
    // Pair 8: Tech MC
    Question(
        id = 15,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "What does HTML stand for?",
        correctAnswer = "HyperText Markup Language",
        options = listOf("HyperText Markup Language", "High Transfer Markup Language", "HyperText Media Language", "Hyperlink Text Markup Language")
    ),
    // Pair 8: Science TypeIn
    Question(
        id = 16,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "What is the speed of light in km/s (approximate whole number)?",
        correctAnswer = "300000",
        acceptedAnswers = listOf("300000", "299792", "300,000", "299,792")
    ),
    // Pair 9: History MC
    Question(
        id = 17,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "Which empire was ruled by Julius Caesar?",
        correctAnswer = "Roman Empire",
        options = listOf("Roman Empire", "Greek Empire", "Ottoman Empire", "Byzantine Empire")
    ),
    // Pair 9: Geography TypeIn
    Question(
        id = 18,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "What is the smallest country in the world?",
        correctAnswer = "Vatican City",
        acceptedAnswers = listOf("vatican city", "vatican", "holy see")
    ),
    // Pair 10: Pop Culture MC
    Question(
        id = 19,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "Which band released the album 'Dark Side of the Moon'?",
        correctAnswer = "Pink Floyd",
        options = listOf("Pink Floyd", "The Beatles", "Led Zeppelin", "The Rolling Stones")
    ),
    // Pair 10: Tech TypeIn
    Question(
        id = 20,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "What does 'AI' stand for in technology?",
        correctAnswer = "Artificial Intelligence",
        acceptedAnswers = listOf("artificial intelligence")
    )
)
