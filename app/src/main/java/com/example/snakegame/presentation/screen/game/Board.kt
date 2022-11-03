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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.snakegame.data.model.GameState
import com.example.snakegame.presentation.theme.Teal500
import com.example.snakegame.util.Constants.BOARD_SIZE

@Composable
fun Board(
    gameState: GameState,
    snakeColor: Color
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
                        color = MaterialTheme.colors.onSurface
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
                    color = MaterialTheme.colors.primary,
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
                        color = snakeColor,
                        shape = MaterialTheme.shapes.medium
                    )
            )
        }
    }
}