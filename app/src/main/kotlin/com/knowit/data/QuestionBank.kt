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
        correctAnswer = "1945"
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
        acceptedAnswers = listOf("rdj", "robert downey")
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
        correctAnswer = "Mars"
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
        acceptedAnswers = listOf("nile river")
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
        correctAnswer = "Python"
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
        acceptedAnswers = listOf("lighthouse of alexandria", "pharos")
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
        acceptedAnswers = listOf("six")
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
        acceptedAnswers = listOf("299792")
    ),
    // Pair 9: History MC
    Question(
        id = 17,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "Julius Caesar was dictator of which ancient state?",
        correctAnswer = "Roman Republic",
        options = listOf("Roman Republic", "Carthage", "Ptolemaic Egypt", "Macedon")
    ),
    // Pair 9: Geography TypeIn
    Question(
        id = 18,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "What is the smallest country in the world?",
        correctAnswer = "Vatican City",
        acceptedAnswers = listOf("vatican", "holy see")
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
        correctAnswer = "Artificial Intelligence"
    ),
    // ── Science (added 2026-10) ──
    Question(
        id = 21,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "What gas do plants absorb from the air for photosynthesis?",
        correctAnswer = "Carbon dioxide",
        options = listOf("Carbon dioxide", "Oxygen", "Nitrogen", "Hydrogen")
    ),
    Question(
        id = 22,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "What is the hardest natural mineral?",
        correctAnswer = "Diamond",
        options = listOf("Diamond", "Quartz", "Topaz", "Corundum")
    ),
    Question(
        id = 23,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "What is the chemical formula for table salt?",
        correctAnswer = "NaCl",
        options = listOf("NaCl", "KCl", "NaOH", "CaCl2")
    ),
    Question(
        id = 24,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "What is the chemical symbol for iron?",
        correctAnswer = "Fe"
    ),
    Question(
        id = 25,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "What force keeps the planets in orbit around the Sun?",
        correctAnswer = "Gravity",
        acceptedAnswers = listOf("gravitation")
    ),
    Question(
        id = 26,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "What is the largest planet in our solar system?",
        correctAnswer = "Jupiter"
    ),
    // ── History (added 2026-10) ──
    Question(
        id = 27,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "In which year did the Berlin Wall fall?",
        correctAnswer = "1989",
        options = listOf("1989", "1991", "1987", "1985")
    ),
    Question(
        id = 28,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "Who was the first person to walk on the Moon?",
        correctAnswer = "Neil Armstrong",
        options = listOf("Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin", "John Glenn")
    ),
    Question(
        id = 29,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "Which ship sank on its maiden voyage in 1912?",
        correctAnswer = "Titanic",
        options = listOf("Titanic", "Lusitania", "Britannic", "Olympic")
    ),
    Question(
        id = 30,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "Which civilization built Machu Picchu?",
        correctAnswer = "Inca",
        acceptedAnswers = listOf("incas", "inca empire")
    ),
    Question(
        id = 31,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "In what year did Christopher Columbus first reach the Americas?",
        correctAnswer = "1492"
    ),
    Question(
        id = 32,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "Which queen reigned over the United Kingdom from 1952 to 2022?",
        correctAnswer = "Elizabeth II",
        acceptedAnswers = listOf("elizabeth", "queen elizabeth", "queen elizabeth ii", "elizabeth 2", "elizabeth the second")
    ),
    // ── Geography (added 2026-10) ──
    Question(
        id = 33,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "What is the largest ocean on Earth?",
        correctAnswer = "Pacific Ocean",
        options = listOf("Pacific Ocean", "Atlantic Ocean", "Indian Ocean", "Arctic Ocean")
    ),
    Question(
        id = 34,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "What is the capital of Australia?",
        correctAnswer = "Canberra",
        options = listOf("Canberra", "Sydney", "Melbourne", "Perth")
    ),
    Question(
        id = 35,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "What is the largest hot desert in the world?",
        correctAnswer = "Sahara",
        options = listOf("Sahara", "Arabian", "Kalahari", "Mojave")
    ),
    Question(
        id = 36,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "What is the tallest mountain on Earth above sea level?",
        correctAnswer = "Mount Everest",
        acceptedAnswers = listOf("everest", "mt everest", "sagarmatha", "chomolungma")
    ),
    Question(
        id = 37,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "What is the capital of Japan?",
        correctAnswer = "Tokyo"
    ),
    Question(
        id = 38,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "On which continent is most of Egypt located?",
        correctAnswer = "Africa"
    ),
    // ── Pop Culture (added 2026-10) ──
    Question(
        id = 39,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "What is the name of the wizarding school in the Harry Potter books?",
        correctAnswer = "Hogwarts",
        options = listOf("Hogwarts", "Durmstrang", "Beauxbatons", "Ilvermorny")
    ),
    Question(
        id = 40,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "Who painted the Mona Lisa?",
        correctAnswer = "Leonardo da Vinci",
        options = listOf("Leonardo da Vinci", "Michelangelo", "Raphael", "Rembrandt")
    ),
    Question(
        id = 41,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "In the classic US edition of Monopoly, how much do you collect for passing Go?",
        correctAnswer = "\$200",
        options = listOf("\$200", "\$100", "\$150", "\$500")
    ),
    Question(
        id = 42,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "Which fictional detective lives at 221B Baker Street?",
        correctAnswer = "Sherlock Holmes",
        acceptedAnswers = listOf("sherlock", "holmes")
    ),
    Question(
        id = 43,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "What is the name of Mickey Mouse's girlfriend?",
        correctAnswer = "Minnie Mouse",
        acceptedAnswers = listOf("minnie")
    ),
    Question(
        id = 44,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "John Lennon and Paul McCartney were members of which band?",
        correctAnswer = "The Beatles"
    ),
    // ── Tech (added 2026-10) ──
    Question(
        id = 45,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "What does 'URL' stand for?",
        correctAnswer = "Uniform Resource Locator",
        options = listOf("Uniform Resource Locator", "Universal Resource Link", "Unified Reference Locator", "Uniform Retrieval Link")
    ),
    Question(
        id = 46,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "Who is credited with inventing the World Wide Web?",
        correctAnswer = "Tim Berners-Lee",
        options = listOf("Tim Berners-Lee", "Vint Cerf", "Bill Gates", "Steve Jobs")
    ),
    Question(
        id = 47,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "How many bits are in a byte?",
        correctAnswer = "8",
        options = listOf("8", "4", "16", "32")
    ),
    Question(
        id = 48,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "What does 'USB' stand for?",
        correctAnswer = "Universal Serial Bus"
    ),
    Question(
        id = 49,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "What does 'RAM' stand for?",
        correctAnswer = "Random Access Memory"
    ),
    Question(
        id = 50,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "Alongside HTML, which language is used to style the look of web pages?",
        correctAnswer = "CSS",
        acceptedAnswers = listOf("cascading style sheets")
    )
)
