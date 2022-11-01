package com.example.snakegame.presentation.screen.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.snakegame.data.model.Coordinate
import com.example.snakegame.domain.game.GameStatus
import com.example.snakegame.domain.game.SnakeDirections

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = hiltViewModel()
) {
    val gameState = gameViewModel.gameStateFlow.collectAsState(initial = null)

    when (gameState.value?.gameStatus) {
        GameStatus.PLAY -> {
            Column(
//            modifier = Modifier.padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                gameState.value?.let {
                    Board(it)
                }
                GameController(
                    onDirectionChange = {
                        when (it) {
                            SnakeDirections.LEFT -> {
                                gameViewModel.makeMove(Coordinate(x = -1, y = 0))
                            }
                            SnakeDirections.RIGHT -> {
                                gameViewModel.makeMove(Coordinate(x = 1, y = 0))
                            }
                            SnakeDirections.UP -> {
                                gameViewModel.makeMove(Coordinate(x = 0, y = -1))
                            }
                            SnakeDirections.DOWN -> {
                                gameViewModel.makeMove(Coordinate(x = 0, y = 1))
                            }
                        }
                    }
                )

            }
        }
        GameStatus.LOSE -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "YOU LOSE")
                Button(onClick = { gameViewModel.restartGame() }) {
                    Text(text = "RESTART")
                }
            }
        }
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { gameViewModel.startGame() }) {
                    Text(text = "START")
                }
            }
        }
    }


}