package com.example.snakegame.domain.game

import androidx.compose.runtime.mutableStateOf
import com.example.snakegame.data.model.Coordinate
import com.example.snakegame.data.model.GameState
import com.example.snakegame.util.Constants.BOARD_SIZE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*

class GameEngine(
    private val scope: CoroutineScope,
    private val onGameEnded: () -> Unit,
    private val onFoodEaten: () -> Unit,
) {
    private val mutex = Mutex()
    private val _gameState = MutableStateFlow(
        GameState(
            foodCoordinate = Coordinate(x = 3, y = 3),
            snakeCoordinates = listOf(Coordinate(x = 5, y = 5)),
            currentDirection = SnakeDirections.RIGHT,
            gameStatus = GameStatus.START
        )
    )
    val gameState: Flow<GameState> = _gameState

    private val currentDirection = mutableStateOf(SnakeDirections.RIGHT)
    private val currentGameStatus = mutableStateOf(GameStatus.START)

    var move = Coordinate(x = 1, y = 0)
        set(value) {
            scope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    fun startGame() {
        currentGameStatus.value = GameStatus.PLAY
        gameLoop()
    }

    private fun gameLoop() {
        scope.launch {
            while (currentGameStatus.value == GameStatus.PLAY) {
                delay(250)
                _gameState.update {
                    updateGame(it)
                }
            }
        }
    }

    private suspend fun updateGame(currentGameState: GameState): GameState {
        var eatenFood = 1
        currentDirection.value = move.toSnakeDirections()
        val hasReachedLeftEnd =
            currentGameState.snakeCoordinates.first().x == 0 &&
                    currentDirection.value == SnakeDirections.LEFT
        val hasReachedRightEnd =
            currentGameState.snakeCoordinates.first().x == BOARD_SIZE - 1 &&
                    currentDirection.value == SnakeDirections.RIGHT
        val hasReachedTopEnd =
            currentGameState.snakeCoordinates.first().y == 0 &&
                    currentDirection.value == SnakeDirections.UP
        val hasReachedBottomEnd =
            currentGameState.snakeCoordinates.first().y == BOARD_SIZE - 1 &&
                    currentDirection.value == SnakeDirections.DOWN
        if (hasReachedLeftEnd || hasReachedTopEnd || hasReachedRightEnd || hasReachedBottomEnd) {
            currentGameStatus.value = GameStatus.LOSE
            onGameEnded()
        }

        val newPosition = currentGameState.snakeCoordinates.first().let { position ->
            mutex.withLock {
                Coordinate(
                    x = (position.x + move.x + BOARD_SIZE) % BOARD_SIZE,
                    y = (position.y + move.y + BOARD_SIZE) % BOARD_SIZE
                )
            }
        }
        val foodPosition = if (newPosition == currentGameState.foodCoordinate) {
            Coordinate(
                x = Random().nextInt(BOARD_SIZE),
                y = Random().nextInt(BOARD_SIZE)
            )
        } else {
            currentGameState.foodCoordinate
        }

        if (newPosition == currentGameState.foodCoordinate) {
            eatenFood = 0
            onFoodEaten()
        }

        if (currentGameState.snakeCoordinates.contains(newPosition)) {
            currentGameStatus.value = GameStatus.LOSE
            onGameEnded()
        }
        val snakeCoordinates = if (currentGameStatus.value != GameStatus.LOSE)
            listOf(newPosition) + currentGameState.snakeCoordinates.dropLast(eatenFood)
        else
            currentGameState.snakeCoordinates
        return currentGameState.copy(
            foodCoordinate = foodPosition,
            snakeCoordinates = snakeCoordinates,
            currentDirection = currentDirection.value,
            gameStatus = currentGameStatus.value
        )
    }

    fun restart() {
        _gameState.update {
            it.copy(
                foodCoordinate = Coordinate(x = 3, y = 3),
                snakeCoordinates = listOf(Coordinate(x = 5, y = 5)),
                currentDirection = SnakeDirections.RIGHT,
                gameStatus = GameStatus.PLAY
            )
        }
        currentDirection.value = SnakeDirections.RIGHT
        move = Coordinate(x = 1, y = 0)
        startGame()
    }
}