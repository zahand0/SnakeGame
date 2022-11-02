package com.example.snakegame.presentation.screen.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.snakegame.R
import com.example.snakegame.data.model.Coordinate
import com.example.snakegame.domain.game.GameStatus
import com.example.snakegame.domain.game.SnakeDirections
import com.example.snakegame.presentation.common.TopBar

@Composable
fun GameScreen(
    navController: NavHostController,
    gameViewModel: GameViewModel = hiltViewModel()
) {
    val gameState = gameViewModel.gameStateFlow.collectAsState(initial = null)
    val score = gameViewModel.score
    val highScores = gameViewModel.getHighScores().collectAsState(initial = listOf())
    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.your_score).format(score.value)) {
                navController.popBackStack()
            }
        },
        backgroundColor = MaterialTheme.colors.background
    ) { paddingValues ->
        when (gameState.value?.gameStatus) {
            GameStatus.PLAY -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
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
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "YOU LOSE")
                    Text(text = "HIGH SCORES")
                    Column {
                        highScores.value.forEach {
                            Text(text = it.toString())
                        }
                    }
                    Button(onClick = { gameViewModel.restartGame() }) {
                        Text(text = "RESTART")
                    }
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
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

}