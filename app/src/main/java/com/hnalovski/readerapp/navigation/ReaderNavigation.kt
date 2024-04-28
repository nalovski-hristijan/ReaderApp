package com.hnalovski.readerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hnalovski.readerapp.screens.ReaderSplashScreen
import com.hnalovski.readerapp.screens.detail.ReaderDetailsScreen
import com.hnalovski.readerapp.screens.login.ReaderLoginScreen
import com.hnalovski.readerapp.screens.search.ReaderSearchScreen
import com.hnalovski.readerapp.screens.stats.ReaderStatsScreen
import com.hnalovski.readerapp.screens.update.ReaderUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {

        composable(route = ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }
        composable(route = ReaderScreens.HomeScreen.name) {
            ReaderSplashScreen(navController = navController)
        }
        composable(route = ReaderScreens.DetailScreen.name) {
            ReaderDetailsScreen(navController = navController)
        }
        composable(route = ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }
        composable(route = ReaderScreens.SearchScreen.name) {
            ReaderSearchScreen(navController = navController)
        }
        composable(route = ReaderScreens.StatsScreen.name) {
            ReaderStatsScreen(navController = navController)
        }
        composable(route = ReaderScreens.UpdateScreen.name) {
            ReaderUpdateScreen(navController = navController)
        }

    }
}