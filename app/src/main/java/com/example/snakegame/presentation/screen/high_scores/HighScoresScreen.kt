package com.example.snakegame.presentation.screen.high_scores

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.snakegame.R
import com.example.snakegame.navigation.Screen
import com.example.snakegame.presentation.common.TopBar

@Composable
fun HighScoresScreen(
    navController: NavHostController,
    highScoresViewModel: HighScoresViewModel = hiltViewModel()
) {
    val highScores = highScoresViewModel.getHighScores().collectAsState(initial = listOf())
    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.high_scores)) {
                navController.popBackStack()
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
    ) { paddingValues ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(paddingValues),
            shape = MaterialTheme.shapes.small,
            backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.3f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
                    .padding(vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                highScores.value.forEachIndexed { index: Int, value: Int ->
                    Text(
                        text = "${index + 1}. $value",
                        style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}