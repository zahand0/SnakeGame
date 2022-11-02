package com.example.snakegame.presentation.screen.high_scores

import androidx.lifecycle.ViewModel
import com.example.snakegame.domain.repository.DataStoreOperations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HighScoresViewModel @Inject constructor(
    private val dataStore: DataStoreOperations
) : ViewModel() {
    fun getHighScores(): Flow<List<Int>> {
        return dataStore.readHighScores()
    }
}