package com.example.snakegame.presentation.screen.game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snakegame.data.model.Coordinate
import com.example.snakegame.domain.game.GameEngine
import com.example.snakegame.domain.repository.DataStoreOperations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val dataStore: DataStoreOperations
) : ViewModel() {

    private val _score = mutableStateOf(0)
    val score: State<Int> = _score
    private val _foodEaten = mutableStateOf(false)
    val foodEaten: State<Boolean> = _foodEaten
    private val _gameOver = mutableStateOf(false)
    val gameOver: State<Boolean> = _gameOver

    private val gameEngine = GameEngine(
        scope = viewModelScope,
        onGameEnded = {
            viewModelScope.launch(Dispatchers.IO) {
                dataStore.saveHighScore(_score.value)
            }
            _gameOver.value = !_gameOver.value
        },
        onFoodEaten = {
            _score.value += 1
            _foodEaten.value = !_foodEaten.value
        }
    )

    val gameStateFlow = gameEngine.gameState

    fun startGame() {
        _score.value = 0
        gameEngine.startGame()
    }

    fun restartGame() {
        _score.value = 0
        gameEngine.restart()
    }

    fun makeMove(move: Coordinate) {
        gameEngine.move = move
    }

}