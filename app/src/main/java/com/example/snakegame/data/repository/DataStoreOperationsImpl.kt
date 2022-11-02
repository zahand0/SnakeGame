package com.example.snakegame.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.snakegame.domain.repository.DataStoreOperations
import com.example.snakegame.util.Constants.PREFERENCES_HIGH_SCORE_1_KEY
import com.example.snakegame.util.Constants.PREFERENCES_HIGH_SCORE_2_KEY
import com.example.snakegame.util.Constants.PREFERENCES_HIGH_SCORE_3_KEY
import com.example.snakegame.util.Constants.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context) : DataStoreOperations {

    private companion object {
        val highScore1Key = intPreferencesKey(name = PREFERENCES_HIGH_SCORE_1_KEY)
        val highScore2Key = intPreferencesKey(name = PREFERENCES_HIGH_SCORE_2_KEY)
        val highScore3Key = intPreferencesKey(name = PREFERENCES_HIGH_SCORE_3_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun saveHighScore(score: Int) {
        dataStore.edit { preferences ->
            val highScore1 = preferences[highScore1Key] ?: 0
            val highScore2 = preferences[highScore2Key] ?: 0
            val highScore3 = preferences[highScore3Key] ?: 0
            if (score >= highScore1) {
                preferences[highScore3Key] = highScore2
                preferences[highScore2Key] = highScore1
                preferences[highScore1Key] = score
            } else {
                if (score >= highScore2) {
                    preferences[highScore3Key] = highScore2
                    preferences[highScore2Key] = score
                } else {
                    if (score >= highScore3) {
                        preferences[highScore3Key] = score
                    }
                }
            }
        }
    }

    override fun readHighScores(): Flow<List<Int>> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val highScore1 = preferences[highScore1Key] ?: 0
                val highScore2 = preferences[highScore2Key] ?: 0
                val highScore3 = preferences[highScore3Key] ?: 0
                val highScores = listOf(highScore1, highScore2, highScore3)
                highScores
            }
    }
}