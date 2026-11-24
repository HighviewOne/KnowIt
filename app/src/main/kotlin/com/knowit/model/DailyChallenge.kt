package com.knowit.model

import java.time.LocalDate
import kotlin.random.Random

/**
 * The daily challenge: the same 10 questions for every player on a given date, picked offline
 * from the date alone (players on the same app version and question bank get the same set).
 * The day turns over at local midnight, like most daily games.
 */
object DailyChallenge {
    /** Difficulty of each question, in play order: it gets harder as you go. */
    val DIFFICULTY_PLAN: List<Difficulty> = listOf(
        Difficulty.EASY, Difficulty.EASY, Difficulty.EASY,
        Difficulty.MEDIUM, Difficulty.MEDIUM, Difficulty.MEDIUM, Difficulty.MEDIUM,
        Difficulty.HARD, Difficulty.HARD, Difficulty.HARD
    )
    val QUESTION_COUNT = DIFFICULTY_PLAN.size

    /** No category appears more than this many times in one challenge (when the bank allows). */
    private const val MAX_PER_CATEGORY = 2

    /** Day #1. Puzzle numbers count up from here. */
    val FIRST_DAY: LocalDate = LocalDate.of(2026, 11, 20)

    /** "Daily #N" for [date]; never below 1 even if the device clock is wrong. */
    fun numberFor(date: LocalDate): Int =
        (date.toEpochDay() - FIRST_DAY.toEpochDay() + 1).coerceAtLeast(1).toInt()

    private fun seedFor(date: LocalDate, salt: Int = 0): Long =
        date.toEpochDay() * 1_000_003L + 0x4B6E6F77L + salt * 7_919L

    /**
     * The challenge for [date]: formats alternate starting with multiple choice, difficulty
     * follows [DIFFICULTY_PLAN], and categories are spread out. If the bank runs short of a
     * difficulty, the slot falls back to any unused question of the same format.
     */
    fun questionsFor(date: LocalDate, bank: List<Question>): List<Question> {
        val random = Random(seedFor(date))
        val remaining = bank.sortedBy { it.id }.shuffled(random).toMutableList()
        val perCategory = mutableMapOf<Category, Int>()
        val picked = mutableListOf<Question>()

        DIFFICULTY_PLAN.forEachIndexed { index, difficulty ->
            val type = if (index % 2 == 0) QuestionType.MULTIPLE_CHOICE else QuestionType.TYPE_IN
            val sameFormat = remaining.filter { it.type == type }
            val candidates = sameFormat.filter { it.difficulty == difficulty }.ifEmpty { sameFormat }.ifEmpty { remaining }
            val choice = candidates.firstOrNull { (perCategory[it.category] ?: 0) < MAX_PER_CATEGORY }
                ?: candidates.firstOrNull()
                ?: return picked
            picked += choice
            remaining -= choice
            perCategory[choice.category] = (perCategory[choice.category] ?: 0) + 1
        }
        return picked
    }

    /** Option order for the multiple-choice question at [index], the same for everyone that day. */
    fun optionOrder(question: Question, date: LocalDate, index: Int): List<String> =
        question.options.shuffled(Random(seedFor(date, salt = index + 1)))

    /** The two wrong options a 50/50 removes from the question at [index]. */
    fun fiftyFiftyRemoves(question: Question, date: LocalDate, index: Int): Set<String> =
        question.options
            .filter { it != question.correctAnswer }
            .shuffled(Random(seedFor(date, salt = 100 + index)))
            .take(2)
            .toSet()

    /** One square per answer: 🟩 correct, 🟨 close, 🟥 wrong. */
    fun grid(outcomes: List<Outcome>): String = outcomes.joinToString("") {
        when (it) {
            Outcome.CORRECT -> "🟩"
            Outcome.CLOSE -> "🟨"
            Outcome.WRONG -> "🟥"
        }
    }
}
