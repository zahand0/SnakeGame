package com.example.snakegame.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.snakegame.presentation.screen.game.GameScreen
import com.example.snakegame.presentation.screen.high_scores.HighScoresScreen
import com.example.snakegame.presentation.screen.menu.MenuScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.firebase.analytics.FirebaseAnalytics

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    firebaseAnalytics: FirebaseAnalytics
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Menu.route
    ) {
        composable(route = Screen.Menu.route) {
            MenuScreen(
                navController = navController,
                firebaseAnalytics = firebaseAnalytics
            )
        }
        composable(
            route = Screen.Game.route,
            enterTransition = { scaleIn() },
            exitTransition = { scaleOut() + fadeOut() }
        ) {
            GameScreen(navController = navController)
        }
        composable(
            route = Screen.HighScores.route,
            enterTransition = { slideInVertically(initialOffsetY = { it }) },
            exitTransition = { slideOutVertically(targetOffsetY = { it }) }
        ) {
            HighScoresScreen(navController = navController)
        }

    }
}