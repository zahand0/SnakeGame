package com.example.snakegame.data.model

import com.example.snakegame.domain.game.GameStatus
import com.example.snakegame.domain.game.SnakeDirections

data class GameState(
    val foodCoordinate: Coordinate,
    val snakeCoordinates: List<Coordinate>,
    val currentDirection: SnakeDirections,
    val gameStatus: GameStatus
)
