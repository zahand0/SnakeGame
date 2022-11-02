package com.example.snakegame.presentation.screen.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.snakegame.domain.game.SnakeDirections
import com.example.snakegame.presentation.theme.Purple500
import com.example.snakegame.presentation.theme.Purple700
import com.example.snakegame.presentation.theme.Shapes

@Composable
fun GameController(onDirectionChange: (SnakeDirections) -> Unit) {
    var currentDirection by remember {
        mutableStateOf(SnakeDirections.RIGHT)
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ControllerButton(icon = Icons.Default.KeyboardArrowLeft) {
            if (currentDirection != SnakeDirections.RIGHT) {
                onDirectionChange(SnakeDirections.LEFT)
                currentDirection = SnakeDirections.LEFT
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ControllerButton(icon = Icons.Default.KeyboardArrowUp) {
                if (currentDirection != SnakeDirections.DOWN) {
                    onDirectionChange(SnakeDirections.UP)
                    currentDirection = SnakeDirections.UP
                }
            }
            Spacer(modifier = Modifier.size(64.dp))
            ControllerButton(icon = Icons.Default.KeyboardArrowDown) {
                if (currentDirection != SnakeDirections.UP) {
                    onDirectionChange(SnakeDirections.DOWN)
                    currentDirection = SnakeDirections.DOWN
                }
            }
        }
        ControllerButton(icon = Icons.Default.KeyboardArrowRight) {
            if (currentDirection != SnakeDirections.LEFT) {
                onDirectionChange(SnakeDirections.RIGHT)
                currentDirection = SnakeDirections.RIGHT
            }
        }
    }
}

@Composable
fun ControllerButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(64.dp)
            .background(
                color = Color.Gray,
                shape = Shapes.medium
            )
            .border(
                width = 3.dp,
                color = MaterialTheme.colors.onSurface,
                shape = Shapes.medium
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Purple500,
            modifier = Modifier.size(64.dp)
        )
    }
}