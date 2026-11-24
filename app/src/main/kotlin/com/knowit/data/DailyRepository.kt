package com.knowit.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.knowit.model.Outcome
import kotlinx.coroutines.flow.first

/**
 * Saved daily-challenge state. Only the latest run is kept: [day] (epoch day) with its answers
 * so far, so a run survives the app being closed and resumes at the next question.
 */
data class DailySave(
    val day: Long = NO_DAY,
    val outcomes: List<Outcome> = emptyList(),
    /** Question index the 50/50 was used on, or -1 if it's still available. */
    val fiftyFiftyIndex: Int = -1,
    val lastCompletedDay: Long = NO_DAY,
    val streak: Int = 0,
    val bestStreak: Int = 0
) {
    companion object {
        const val NO_DAY = Long.MIN_VALUE
    }

    /** Answers recorded for [epochDay] (empty if the saved run is from another day). */
    fun outcomesFor(epochDay: Long): List<Outcome> = if (day == epochDay) outcomes else emptyList()

    /** 50/50 index used on [epochDay], or -1. */
    fun fiftyFiftyIndexFor(epochDay: Long): Int = if (day == epochDay) fiftyFiftyIndex else -1

    /** The streak as of [today]: it lapses once a whole day goes by without finishing a challenge. */
    fun currentStreak(today: Long): Int =
        if (lastCompletedDay != NO_DAY && lastCompletedDay >= today - 1) streak else 0

    /** Records [epochDay] as finished: extends the streak if yesterday was finished too. */
    fun completed(epochDay: Long): DailySave {
        if (lastCompletedDay == epochDay) return this
        val newStreak = if (lastCompletedDay == epochDay - 1) streak + 1 else 1
        return copy(lastCompletedDay = epochDay, streak = newStreak, bestStreak = maxOf(bestStreak, newStreak))
    }
}

class DailyRepository(private val dataStore: DataStore<Preferences>) {
    constructor(context: Context) : this(context.knowItPrefs)

    private companion object {
        val DAY = longPreferencesKey("daily_day")
        val OUTCOMES = stringPreferencesKey("daily_outcomes")
        val FIFTY_FIFTY_INDEX = intPreferencesKey("daily_fifty_fifty_index")
        val LAST_COMPLETED_DAY = longPreferencesKey("daily_last_completed_day")
        val STREAK = intPreferencesKey("daily_streak")
        val BEST_STREAK = intPreferencesKey("daily_best_streak")

        // One letter per answer; never reorder or reuse these, they are stored on devices.
        val CODES = mapOf(Outcome.CORRECT to 'C', Outcome.CLOSE to 'H', Outcome.WRONG to 'W')
    }

    suspend fun load(): DailySave {
        val prefs = dataStore.data.first()
        return DailySave(
            day = prefs[DAY] ?: DailySave.NO_DAY,
            outcomes = prefs[OUTCOMES].orEmpty().mapNotNull { code -> CODES.entries.firstOrNull { it.value == code }?.key },
            fiftyFiftyIndex = prefs[FIFTY_FIFTY_INDEX] ?: -1,
            lastCompletedDay = prefs[LAST_COMPLETED_DAY] ?: DailySave.NO_DAY,
            streak = prefs[STREAK] ?: 0,
            bestStreak = prefs[BEST_STREAK] ?: 0
        )
    }

    suspend fun save(save: DailySave) {
        dataStore.edit { prefs ->
            prefs[DAY] = save.day
            prefs[OUTCOMES] = save.outcomes.map { CODES.getValue(it) }.joinToString("")
            prefs[FIFTY_FIFTY_INDEX] = save.fiftyFiftyIndex
            prefs[LAST_COMPLETED_DAY] = save.lastCompletedDay
            prefs[STREAK] = save.streak
            prefs[BEST_STREAK] = save.bestStreak
        }
    }
}
