package com.example.snakegame.presentation.screen.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.snakegame.data.model.GameState
import com.example.snakegame.util.Constants.BOARD_SIZE

@Composable
fun Board(
    gameState: GameState
) {
    BoxWithConstraints(
        modifier = Modifier.padding(16.dp)
    ) {
        val tileSize = maxWidth / BOARD_SIZE
        Box(
            modifier = Modifier
                .size(maxWidth)
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colors.primary
                    )
                )
        )
        Box(
            modifier = Modifier
                .offset(
                    x = tileSize * gameState.foodCoordinate.x,
                    y = tileSize * gameState.foodCoordinate.y
                )
                .size(tileSize / 2)
                .offset(tileSize / 4, tileSize / 4)
                .background(
                    color = MaterialTheme.colors.secondary,
                    shape = CircleShape
                )
        )
        gameState.snakeCoordinates.forEach { coordinate ->
            Box(
                modifier = Modifier
                    .offset(
                        x = tileSize * coordinate.x,
                        y = tileSize * coordinate.y
                    )
                    .size(tileSize)
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = MaterialTheme.shapes.medium
                    )
            )
        }
    }
}