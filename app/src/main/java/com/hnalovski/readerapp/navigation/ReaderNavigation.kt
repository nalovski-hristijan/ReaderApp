package com.hnalovski.readerapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hnalovski.readerapp.screens.ReaderSplashScreen
import com.hnalovski.readerapp.screens.detail.ReaderDetailsScreen
import com.hnalovski.readerapp.screens.home.ReaderHomeScreen
import com.hnalovski.readerapp.screens.home.ReaderHomeViewModel
import com.hnalovski.readerapp.screens.login.ReaderLoginScreen
import com.hnalovski.readerapp.screens.search.ReaderSearchScreen
import com.hnalovski.readerapp.screens.search.ReaderSearchViewModel
import com.hnalovski.readerapp.screens.stats.ReaderStatsScreen
import com.hnalovski.readerapp.screens.update.ReaderBookUpdateScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {

        composable(route = ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }

        composable(route = ReaderScreens.HomeScreen.name) {
            val viewModel = hiltViewModel<ReaderHomeViewModel>()
            ReaderHomeScreen(navController = navController, viewModel = viewModel)
        }

        val detailsName = ReaderScreens.DetailScreen.name
        composable(
            route = "$detailsName/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId")
                .let { ReaderDetailsScreen(navController = navController, bookId = it.toString()) }

        }

        composable(route = ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }

        composable(route = ReaderScreens.SearchScreen.name) {
            val viewModel = hiltViewModel<ReaderSearchViewModel>()
            ReaderSearchScreen(navController = navController, viewModel)
        }

        composable(route = ReaderScreens.StatsScreen.name) {
            val viewModel = hiltViewModel<ReaderHomeViewModel>()
            ReaderStatsScreen(navController = navController, viewModel = viewModel )
        }

        val updateName = ReaderScreens.UpdateScreen.name
        composable("$updateName/{bookItemId}", arguments = listOf(navArgument("bookItemId") {
            type = NavType.StringType
        })) { backStack ->

            backStack.arguments?.getString("bookItemId").let {
                ReaderBookUpdateScreen(navController = navController, bookItemId = it.toString())
            }
        }

    }
}