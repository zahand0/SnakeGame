package com.example.snakegame.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {

    suspend fun saveHighScore(score: Int)

    fun readHighScores(): Flow<List<Int>>
}