package com.example.snakegame.domain.game

import com.example.snakegame.data.model.Coordinate

fun Coordinate.toSnakeDirections():SnakeDirections {
    return when {
        this.x == 0 && this.y == -1 -> SnakeDirections.UP
        this.x == 0 && this.y == 1 -> SnakeDirections.DOWN
        this.x == -1 && this.y == 0 -> SnakeDirections.LEFT
        this.x == 1 && this.y == 0 -> SnakeDirections.RIGHT
        else -> throw (IllegalStateException("Move can't be in (${this.x}, ${this.y}) state."))
    }
}