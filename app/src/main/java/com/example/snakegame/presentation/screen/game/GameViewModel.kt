package com.example.snakegame.presentation.screen.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snakegame.data.model.Coordinate
import com.example.snakegame.domain.game.GameEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(

) : ViewModel() {
//    private lateinit var scope: CoroutineScope

    private val gameEngine = GameEngine(
        scope = viewModelScope,
        onGameEnded = {

        },
        onFoodEaten = {

        }
    )

    val gameStateFlow = gameEngine.gameState

    fun startGame() {
        gameEngine.startGame()
    }

    fun restartGame() {
        gameEngine.restart()
    }

    fun makeMove(move: Coordinate) {
        gameEngine.move = move
    }

}