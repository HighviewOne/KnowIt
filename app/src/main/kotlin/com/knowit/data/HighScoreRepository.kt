package com.knowit.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "knowit_prefs")

class HighScoreRepository(private val context: Context) {
    companion object {
        private val HIGH_SCORE_KEY = intPreferencesKey("high_score")
    }

    val highScoreFlow: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[HIGH_SCORE_KEY] ?: 0
    }

    suspend fun saveHighScore(score: Int) {
        context.dataStore.edit { prefs ->
            val currentHigh = prefs[HIGH_SCORE_KEY] ?: 0
            if (score > currentHigh) {
                prefs[HIGH_SCORE_KEY] = score
            }
        }
    }

    suspend fun getHighScore(): Int {
        return context.dataStore.data.map { prefs ->
            prefs[HIGH_SCORE_KEY] ?: 0
        }.first()
    }
}
