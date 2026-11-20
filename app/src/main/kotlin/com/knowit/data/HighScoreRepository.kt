package com.knowit.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.first

class HighScoreRepository(private val dataStore: DataStore<Preferences>) {
    // Held by a ViewModel that outlives the Activity, so never keep an Activity context.
    constructor(context: Context) : this(context.knowItPrefs)

    companion object {
        private val HIGH_SCORE_KEY = intPreferencesKey("high_score")
    }

    /** Stores [score] only if it beats the saved best. */
    suspend fun saveHighScore(score: Int) {
        dataStore.edit { prefs ->
            val currentHigh = prefs[HIGH_SCORE_KEY] ?: 0
            if (score > currentHigh) {
                prefs[HIGH_SCORE_KEY] = score
            }
        }
    }

    suspend fun getHighScore(): Int = dataStore.data.first()[HIGH_SCORE_KEY] ?: 0
}
