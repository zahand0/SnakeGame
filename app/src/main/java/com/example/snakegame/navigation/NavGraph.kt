package com.example.snakegame.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.snakegame.presentation.screen.game.GameScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Game.route
    ) {
        composable(route = Screen.Menu.route) {

        }
        composable(route = Screen.Game.route) {
            GameScreen()
        }
        composable(route = Screen.HighScores.route) {

        }

    }
}