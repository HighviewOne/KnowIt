package com.knowit.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knowit.data.HighScoreRepository

class GameViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val appContext = context.applicationContext

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return GameViewModel(HighScoreRepository(appContext)) as T
    }
}
