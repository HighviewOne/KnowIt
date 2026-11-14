package com.knowit.data

import com.knowit.model.Category
import com.knowit.model.Difficulty
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
        options = listOf("Wings", "Sunrise", "The Jazz Singer", "7th Heaven"),
        difficulty = Difficulty.HARD
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
        acceptedAnswers = listOf("lighthouse of alexandria", "pharos"),
        difficulty = Difficulty.MEDIUM
    ),
    // Pair 7: Geography MC
    Question(
        id = 13,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "Which country has the most natural lakes?",
        correctAnswer = "Canada",
        options = listOf("Canada", "Russia", "USA", "Finland"),
        difficulty = Difficulty.MEDIUM
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
        acceptedAnswers = listOf("299792"),
        difficulty = Difficulty.MEDIUM
    ),
    // Pair 9: History MC
    Question(
        id = 17,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "Julius Caesar was dictator of which ancient state?",
        correctAnswer = "Roman Republic",
        options = listOf("Roman Republic", "Carthage", "Ptolemaic Egypt", "Macedon"),
        difficulty = Difficulty.MEDIUM
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
    ),
    // ── Science (added 2026-11) ──
    Question(
        id = 51,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "What is the most abundant gas in Earth's atmosphere?",
        correctAnswer = "Nitrogen",
        options = listOf("Nitrogen", "Oxygen", "Argon", "Carbon dioxide"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 52,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "In DNA, which base pairs with adenine?",
        correctAnswer = "Thymine",
        options = listOf("Thymine", "Uracil", "Guanine", "Cytosine"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 53,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "Which metal is liquid at room temperature?",
        correctAnswer = "Mercury",
        options = listOf("Mercury", "Gallium", "Tin", "Lead"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 54,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "What is the SI unit of electrical resistance?",
        correctAnswer = "Ohm",
        options = listOf("Ohm", "Volt", "Ampere", "Farad"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 55,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "Which planet has the shortest day, spinning once in about 10 hours?",
        correctAnswer = "Jupiter",
        options = listOf("Jupiter", "Saturn", "Earth", "Neptune"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 56,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "What is the half-life of carbon-14?",
        correctAnswer = "5,730 years",
        options = listOf("5,730 years", "1,600 years", "24,100 years", "50,000 years"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 57,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "Which astronomer formulated the three laws of planetary motion?",
        correctAnswer = "Johannes Kepler",
        options = listOf("Johannes Kepler", "Isaac Newton", "Galileo Galilei", "Nicolaus Copernicus"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 58,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "What is the most abundant element in Earth's crust by mass?",
        correctAnswer = "Oxygen",
        options = listOf("Oxygen", "Silicon", "Aluminum", "Iron"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 59,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "Which part of the brain coordinates balance and fine movement?",
        correctAnswer = "Cerebellum",
        options = listOf("Cerebellum", "Medulla oblongata", "Hypothalamus", "Frontal lobe"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 60,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.SCIENCE,
        questionText = "Which subatomic particle did J. J. Thomson discover in 1897?",
        correctAnswer = "Electron",
        options = listOf("Electron", "Proton", "Neutron", "Positron"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 61,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "What is the chemical symbol for potassium?",
        correctAnswer = "K",
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 62,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "What is the largest organ of the human body?",
        correctAnswer = "Skin",
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 63,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "What is the name for the point in a planet's orbit where it is closest to the Sun?",
        correctAnswer = "Perihelion",
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 64,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "What is the hardest substance in the human body?",
        correctAnswer = "Tooth enamel",
        acceptedAnswers = listOf("enamel"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 65,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "Which element has the chemical symbol W?",
        correctAnswer = "Tungsten",
        acceptedAnswers = listOf("wolfram"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 66,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "How many chromosomes are in a typical human body cell?",
        correctAnswer = "46",
        acceptedAnswers = listOf("forty six", "23 pairs"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 67,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "What is the nearest large spiral galaxy to the Milky Way?",
        correctAnswer = "Andromeda",
        acceptedAnswers = listOf("andromeda galaxy", "m31", "messier 31"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 68,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "Which element makes up most of the Sun's mass?",
        correctAnswer = "Hydrogen",
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 69,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "What protein in red blood cells carries oxygen?",
        correctAnswer = "Hemoglobin",
        acceptedAnswers = listOf("haemoglobin"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 70,
        type = QuestionType.TYPE_IN,
        category = Category.SCIENCE,
        questionText = "What is the boundary around a black hole, beyond which nothing can escape, called?",
        correctAnswer = "Event horizon",
        difficulty = Difficulty.HARD
    ),
    // ── History (added 2026-11) ──
    Question(
        id = 71,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "Suleiman the Magnificent ruled which empire?",
        correctAnswer = "Ottoman Empire",
        options = listOf("Ottoman Empire", "Mughal Empire", "Safavid Empire", "Byzantine Empire"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 72,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "In what year was Magna Carta sealed by King John?",
        correctAnswer = "1215",
        options = listOf("1215", "1066", "1314", "1381"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 73,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "At which 1815 battle was Napoleon finally defeated?",
        correctAnswer = "Waterloo",
        options = listOf("Waterloo", "Austerlitz", "Leipzig", "Trafalgar"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 74,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "Who was the first emperor of a unified China?",
        correctAnswer = "Qin Shi Huang",
        options = listOf("Qin Shi Huang", "Kublai Khan", "Liu Bang", "Wu Zetian"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 75,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "Which treaty ended World War I between the Allies and Germany?",
        correctAnswer = "Treaty of Versailles",
        options = listOf("Treaty of Versailles", "Treaty of Trianon", "Treaty of Brest-Litovsk", "Peace of Westphalia"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 76,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "Whose nearly intact tomb did Howard Carter discover in 1922?",
        correctAnswer = "Tutankhamun",
        options = listOf("Tutankhamun", "Ramesses II", "Khufu", "Akhenaten"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 77,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "How many years did the Hundred Years' War (1337–1453) actually last?",
        correctAnswer = "116",
        options = listOf("116", "100", "99", "130"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 78,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "What was the capital of the Byzantine Empire?",
        correctAnswer = "Constantinople",
        options = listOf("Constantinople", "Rome", "Athens", "Antioch"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 79,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "Who was the British prime minister when World War II began in 1939?",
        correctAnswer = "Neville Chamberlain",
        options = listOf("Neville Chamberlain", "Winston Churchill", "Clement Attlee", "Stanley Baldwin"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 80,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.HISTORY,
        questionText = "Which Mongol ruler founded China's Yuan dynasty?",
        correctAnswer = "Kublai Khan",
        options = listOf("Kublai Khan", "Genghis Khan", "Ögedei Khan", "Timur"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 81,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "Name a Roman town buried by the eruption of Mount Vesuvius in AD 79.",
        correctAnswer = "Pompeii",
        acceptedAnswers = listOf("herculaneum", "stabiae", "oplontis"),
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 82,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "In what year was the US Declaration of Independence adopted?",
        correctAnswer = "1776",
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 83,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "In what year was the Battle of Hastings fought?",
        correctAnswer = "1066",
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 84,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "Which queen of France was executed by guillotine in 1793?",
        correctAnswer = "Marie Antoinette",
        acceptedAnswers = listOf("antoinette", "queen marie antoinette"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 85,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "What was the name of the first artificial satellite, launched by the Soviet Union in 1957?",
        correctAnswer = "Sputnik",
        acceptedAnswers = listOf("sputnik 1"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 86,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "Which Carthaginian general crossed the Alps with war elephants to invade Italy?",
        correctAnswer = "Hannibal",
        acceptedAnswers = listOf("hannibal barca"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 87,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "In what year did the storming of the Bastille take place?",
        correctAnswer = "1789",
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 88,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "Who was the last tsar of Russia?",
        correctAnswer = "Nicholas II",
        acceptedAnswers = listOf("tsar nicholas ii", "czar nicholas ii", "nicholas 2", "nicholas the second", "tsar nicholas", "nicholas"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 89,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "Which English king had six wives?",
        correctAnswer = "Henry VIII",
        acceptedAnswers = listOf("king henry viii", "henry 8", "henry the eighth", "henry"),
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 90,
        type = QuestionType.TYPE_IN,
        category = Category.HISTORY,
        questionText = "In what year is the Western Roman Empire traditionally said to have fallen?",
        correctAnswer = "476",
        acceptedAnswers = listOf("476 ad", "ad 476"),
        difficulty = Difficulty.HARD
    ),
    // ── Geography (added 2026-11) ──
    Question(
        id = 91,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "What is the capital of Canada?",
        correctAnswer = "Ottawa",
        options = listOf("Ottawa", "Toronto", "Montreal", "Vancouver"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 92,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "Counting its overseas territories, which country spans the most time zones?",
        correctAnswer = "France",
        options = listOf("France", "Russia", "USA", "United Kingdom"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 93,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "Which river flows through Baghdad?",
        correctAnswer = "Tigris",
        options = listOf("Tigris", "Euphrates", "Jordan", "Nile"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 94,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "What is the capital of Kazakhstan?",
        correctAnswer = "Astana",
        options = listOf("Astana", "Almaty", "Tashkent", "Bishkek"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 95,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "Which strait separates Europe from Africa at the western end of the Mediterranean?",
        correctAnswer = "Strait of Gibraltar",
        options = listOf("Strait of Gibraltar", "Bosporus", "Strait of Hormuz", "Strait of Messina"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 96,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "What is the deepest lake in the world?",
        correctAnswer = "Lake Baikal",
        options = listOf("Lake Baikal", "Lake Tanganyika", "Caspian Sea", "Lake Superior"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 97,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "Which African country has the largest population?",
        correctAnswer = "Nigeria",
        options = listOf("Nigeria", "Ethiopia", "Egypt", "DR Congo"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 98,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.GEOGRAPHY,
        questionText = "Which country is completely surrounded by South Africa?",
        correctAnswer = "Lesotho",
        options = listOf("Lesotho", "Eswatini", "Botswana", "Namibia"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 99,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "What is the capital of Turkey?",
        correctAnswer = "Ankara",
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 100,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "What is the largest country in South America by area?",
        correctAnswer = "Brazil",
        acceptedAnswers = listOf("brasil"),
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 101,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "What is the longest mountain range on land?",
        correctAnswer = "Andes",
        acceptedAnswers = listOf("andes mountains"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 102,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "Which US state is made up entirely of islands?",
        correctAnswer = "Hawaii",
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 103,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "What is the capital of New Zealand?",
        correctAnswer = "Wellington",
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 104,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "Which salt lake between Israel and Jordan has the lowest shoreline on land?",
        correctAnswer = "Dead Sea",
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 105,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "Which narrow strip of land connects North and South America?",
        correctAnswer = "Isthmus of Panama",
        acceptedAnswers = listOf("panama", "panama isthmus"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 106,
        type = QuestionType.TYPE_IN,
        category = Category.GEOGRAPHY,
        questionText = "Which country has the longest coastline in the world?",
        correctAnswer = "Canada",
        difficulty = Difficulty.HARD
    ),
    // ── Pop Culture (added 2026-11) ──
    Question(
        id = 107,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "Who composed the violin concertos known as The Four Seasons?",
        correctAnswer = "Antonio Vivaldi",
        options = listOf("Antonio Vivaldi", "Johann Sebastian Bach", "George Frideric Handel", "Wolfgang Amadeus Mozart"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 108,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "In what year was the first Star Wars film released?",
        correctAnswer = "1977",
        options = listOf("1977", "1975", "1979", "1980"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 109,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "Who painted 'The Persistence of Memory', with its melting clocks?",
        correctAnswer = "Salvador Dalí",
        options = listOf("Salvador Dalí", "René Magritte", "Max Ernst", "Joan Miró"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 110,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "Which novel begins with the line 'Call me Ishmael'?",
        correctAnswer = "Moby-Dick",
        options = listOf("Moby-Dick", "Treasure Island", "The Old Man and the Sea", "Robinson Crusoe"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 111,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "On which Queen album did 'Bohemian Rhapsody' first appear?",
        correctAnswer = "A Night at the Opera",
        options = listOf("A Night at the Opera", "News of the World", "Sheer Heart Attack", "A Day at the Races"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 112,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "In chess, which piece can only move diagonally?",
        correctAnswer = "Bishop",
        options = listOf("Bishop", "Rook", "Knight", "Queen"),
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 113,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "Who wrote the novel 'One Hundred Years of Solitude'?",
        correctAnswer = "Gabriel García Márquez",
        options = listOf("Gabriel García Márquez", "Jorge Luis Borges", "Isabel Allende", "Mario Vargas Llosa"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 114,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.POP_CULTURE,
        questionText = "What was Walt Disney's first feature-length animated film?",
        correctAnswer = "Snow White and the Seven Dwarfs",
        options = listOf("Snow White and the Seven Dwarfs", "Pinocchio", "Fantasia", "Bambi"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 115,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "What is the name of Black Panther's fictional African homeland?",
        correctAnswer = "Wakanda",
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 116,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "Who wrote the play 'Romeo and Juliet'?",
        correctAnswer = "William Shakespeare",
        acceptedAnswers = listOf("shakespeare"),
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 117,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "Which hobbit did Elijah Wood play in The Lord of the Rings films?",
        correctAnswer = "Frodo",
        acceptedAnswers = listOf("frodo baggins"),
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 118,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "Which composer wrote the 'Moonlight' Sonata?",
        correctAnswer = "Ludwig van Beethoven",
        acceptedAnswers = listOf("beethoven", "van beethoven"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 119,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "How many squares are on a chessboard?",
        correctAnswer = "64",
        acceptedAnswers = listOf("sixty four"),
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 120,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "What is the name of Captain Ahab's ship in Moby-Dick?",
        correctAnswer = "Pequod",
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 121,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "In Greek mythology, who is the king of the gods?",
        correctAnswer = "Zeus",
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 122,
        type = QuestionType.TYPE_IN,
        category = Category.POP_CULTURE,
        questionText = "Which Dutch painter cut off part of his own ear in 1888?",
        correctAnswer = "Vincent van Gogh",
        acceptedAnswers = listOf("van gogh"),
        difficulty = Difficulty.MEDIUM
    ),
    // ── Tech (added 2026-11) ──
    Question(
        id = 123,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "What does 'HTTP' stand for?",
        correctAnswer = "HyperText Transfer Protocol",
        options = listOf("HyperText Transfer Protocol", "HyperText Transmission Protocol", "High Transfer Text Protocol", "Hyperlink Transfer Protocol"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 124,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "Who co-founded Apple with Steve Jobs and Ronald Wayne?",
        correctAnswer = "Steve Wozniak",
        options = listOf("Steve Wozniak", "Bill Gates", "Paul Allen", "Larry Page"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 125,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "Which company created the JavaScript language?",
        correctAnswer = "Netscape",
        options = listOf("Netscape", "Microsoft", "Sun Microsystems", "Oracle"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 126,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "In what year did the first iPhone go on sale?",
        correctAnswer = "2007",
        options = listOf("2007", "2005", "2008", "2010"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 127,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "Who is often called the first computer programmer, for her notes on Babbage's Analytical Engine?",
        correctAnswer = "Ada Lovelace",
        options = listOf("Ada Lovelace", "Grace Hopper", "Hedy Lamarr", "Margaret Hamilton"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 128,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "What does 'SQL' stand for?",
        correctAnswer = "Structured Query Language",
        options = listOf("Structured Query Language", "Simple Query Language", "Standard Question Language", "Sequential Query Logic"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 129,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "Which port does HTTPS use by default?",
        correctAnswer = "443",
        options = listOf("443", "80", "22", "8080"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 130,
        type = QuestionType.MULTIPLE_CHOICE,
        category = Category.TECH,
        questionText = "Which British mathematician proposed the 'imitation game' test of machine intelligence?",
        correctAnswer = "Alan Turing",
        options = listOf("Alan Turing", "Charles Babbage", "John von Neumann", "Claude Shannon"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 131,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "What does 'GPU' stand for?",
        correctAnswer = "Graphics Processing Unit",
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 132,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "What does 'PDF' stand for?",
        correctAnswer = "Portable Document Format",
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 133,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "What is the binary number 1010 in decimal?",
        correctAnswer = "10",
        acceptedAnswers = listOf("ten"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 134,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "Which company makes the Windows operating system?",
        correctAnswer = "Microsoft",
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 135,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "Who created the Linux kernel?",
        correctAnswer = "Linus Torvalds",
        acceptedAnswers = listOf("torvalds"),
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = 136,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "What does 'LAN' stand for?",
        correctAnswer = "Local Area Network",
        difficulty = Difficulty.EASY
    ),
    Question(
        id = 137,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "How many bits are in an IPv4 address?",
        correctAnswer = "32",
        acceptedAnswers = listOf("thirty two"),
        difficulty = Difficulty.HARD
    ),
    Question(
        id = 138,
        type = QuestionType.TYPE_IN,
        category = Category.TECH,
        questionText = "Which Sun Microsystems language promised 'write once, run anywhere'?",
        correctAnswer = "Java",
        difficulty = Difficulty.MEDIUM
    )
)
