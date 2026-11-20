package com.knowit.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// One DataStore per file per process, shared by every repository.
private val Context.dataStore by preferencesDataStore(name = "knowit_prefs")

/** The app's preferences store. Always taken from the application context. */
internal val Context.knowItPrefs: DataStore<Preferences>
    get() = applicationContext.dataStore
