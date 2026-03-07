package com.knowit.viewmodel

import android.content.Context
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.knowit.data.HighScoreRepository

class GameViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val context: Context
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        val repository = HighScoreRepository(context)
        @Suppress("UNCHECKED_CAST")
        return GameViewModel(handle, repository) as T
    }
}
