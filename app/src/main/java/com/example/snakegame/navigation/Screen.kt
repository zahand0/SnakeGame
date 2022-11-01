package com.example.snakegame.navigation

sealed class Screen(val route: String) {
    object Menu : Screen(route = "menu_screen")
    object Game : Screen(route = "game_screen")
    object HighScores : Screen(route = "high_scores_screen")
}
