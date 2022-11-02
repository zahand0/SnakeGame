package com.example.snakegame.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.snakegame.presentation.screen.game.GameScreen
import com.example.snakegame.presentation.screen.high_scores.HighScoresScreen
import com.example.snakegame.presentation.screen.menu.MenuScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route
    ) {
        composable(route = Screen.Menu.route) {
            MenuScreen(navController = navController)
        }
        composable(route = Screen.Game.route) {
            GameScreen(navController = navController)
        }
        composable(route = Screen.HighScores.route) {
            HighScoresScreen(navController = navController)
        }

    }
}