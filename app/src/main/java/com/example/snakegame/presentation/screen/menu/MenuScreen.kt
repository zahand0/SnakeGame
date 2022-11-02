package com.example.snakegame.presentation.screen.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.navigation.NavHostController
import com.example.snakegame.R
import com.example.snakegame.navigation.Screen

@Composable
fun MenuScreen(
    navController: NavHostController
) {
    BoxWithConstraints(
        modifier = Modifier.padding(16.dp)
    ) {
        val minSide = min(maxHeight, maxWidth)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_snake),
                contentDescription = stringResource(
                    R.string.snake_icon
                ),
                modifier = Modifier
                    .size(minSide / 2)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(16.dp))
            MenuButton(
                modifier = Modifier.width(minSide / 2),
                text = stringResource(R.string.new_game)
            ) {
                navController.navigate(Screen.Game.route)
            }
            Spacer(modifier = Modifier.height(16.dp))
            MenuButton(
                modifier = Modifier.width(minSide / 2),
                text = stringResource(R.string.high_scores)
            ) {
                navController.navigate(Screen.HighScores.route)
            }
        }
    }

}

@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {
            onClick()
        },
        shape = CircleShape
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onPrimary
        )
    }
}