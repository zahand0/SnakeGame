package com.example.snakegame.presentation.screen.game

import android.content.Context
import android.content.res.Configuration
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.snakegame.R
import com.example.snakegame.data.model.Coordinate
import com.example.snakegame.data.model.GameState
import com.example.snakegame.domain.game.GameStatus
import com.example.snakegame.domain.game.SnakeDirections
import com.example.snakegame.presentation.common.TopBar
import com.example.snakegame.presentation.theme.Red500
import com.example.snakegame.presentation.theme.Teal500

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameScreen(
    navController: NavHostController,
    gameViewModel: GameViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val gameState = gameViewModel.gameStateFlow.collectAsState(initial = null)
    val score = gameViewModel.score

    var isLose by remember { mutableStateOf(true) }
    var isBoardVisible by remember { mutableStateOf(false) }

    val isFoodEaten by remember {
        gameViewModel.foodEaten
    }
    val isGameOver by remember {
        gameViewModel.gameOver
    }
    LaunchedEffect(key1 = isFoodEaten) {
        if (!isLose && isBoardVisible)
            playFoodEatenSound(context)
    }
    LaunchedEffect(key1 = isGameOver) {
        if (isLose)
            playGameOverSound(context)
    }
    val snakeAnimatedColor = animateColorAsState(
        if (isLose) Red500 else Teal500,
        animationSpec = repeatable(
            iterations = 5,
            animation = tween(durationMillis = 250),
            repeatMode = RepeatMode.Reverse
        )
    )
    val snakeBaseColor = remember {
        Teal500
    }
    val snakeColor = remember {
        mutableStateOf(snakeBaseColor)
    }



    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.your_score).format(score.value)) {
                navController.popBackStack()
            }
        },
        backgroundColor = MaterialTheme.colors.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            // start button
            if (!isLose && !isBoardVisible) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(
                        modifier = Modifier.width(150.dp),
                        onClick = {
                            gameViewModel.startGame()
                        },
                        shape = CircleShape
                    ) {
                        Text(
                            text = stringResource(R.string.start),
                            style = MaterialTheme.typography.h3,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
            // board
            AnimatedVisibility(
                visible = isBoardVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                GameplayElements(
                    gameState = gameState,
                    snakeColor = snakeColor,
                    gameViewModel = gameViewModel
                )
            }

            // restart
            AnimatedVisibility(
                visible = isLose,
                enter = scaleIn(),
                exit = ExitTransition.None
            ) {
                RestartCard(
                    score = score.value,
                    gameViewModel = gameViewModel
                )
            }
            when (gameState.value?.gameStatus) {
                GameStatus.PLAY -> {
                    snakeColor.value = snakeBaseColor
                    isLose = false
                    isBoardVisible = true
                }
                GameStatus.LOSE -> {
                    snakeColor.value = snakeAnimatedColor.value
                    isLose = true
                    isBoardVisible = true
                }
                else -> {
                    isLose = false
                    isBoardVisible = false
                }
            }
        }
    }
}

@Composable
fun GameContent(
    gameState: State<GameState?>,
    snakeColor: State<Color>,
    gameViewModel: GameViewModel
) {
    gameState.value?.let {
        Board(
            gameState = it,
            snakeColor = snakeColor.value
        )
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

@Composable
fun RestartCard(
    score: Int,
    gameViewModel: GameViewModel
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = MaterialTheme.shapes.small,
            backgroundColor = MaterialTheme.colors.surface,
            border = BorderStroke(2.dp, MaterialTheme.colors.primaryVariant)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = stringResource(id = R.string.your_score).format(score),
                    style = MaterialTheme.typography.h1
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    modifier = Modifier.width(150.dp),
                    onClick = {
                        gameViewModel.restartGame()
                    },
                    shape = CircleShape
                ) {
                    Text(
                        text = stringResource(R.string.restart),
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onPrimary
                    )
                }

            }
        }
    }
}


@Composable
fun GameplayElements(
    gameState: State<GameState?>,
    snakeColor: State<Color>,
    gameViewModel: GameViewModel
) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                GameContent(
                    gameState = gameState,
                    snakeColor = snakeColor,
                    gameViewModel = gameViewModel
                )
            }
        }
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                GameContent(
                    gameState = gameState,
                    snakeColor = snakeColor,
                    gameViewModel = gameViewModel
                )

            }
        }
    }
}

fun playFoodEatenSound(
    context: Context
) {
    try {
        val mediaPlayer = MediaPlayer.create(context, R.raw.eating)
        mediaPlayer.start()
    } catch (e: Exception) {
        Log.e("GameScreen", "can't play sound eating", e)
    }

}

fun playGameOverSound(
    context: Context
) {
    try {
        val mediaPlayer = MediaPlayer.create(context, R.raw.game_over)
        mediaPlayer.start()
    } catch (e: Exception) {
        Log.e("GameScreen", "can't play sound eating", e)
    }

}