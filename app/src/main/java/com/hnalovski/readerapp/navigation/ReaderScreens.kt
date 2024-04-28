package com.hnalovski.readerapp.navigation

import java.lang.IllegalArgumentException

enum class ReaderScreens {
    SplashScreen,
    DetailScreen,
    HomeScreen,
    LoginScreen,
    SearchScreen,
    StatsScreen,
    UpdateScreen,
    CreateAccountScreen;

    companion object {
        fun fromRoute(route: String?): ReaderScreens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            DetailScreen.name -> DetailScreen
            HomeScreen.name -> LoginScreen
            SearchScreen.name -> SearchScreen
            StatsScreen.name -> StatsScreen
            UpdateScreen.name -> UpdateScreen
            CreateAccountScreen.name -> CreateAccountScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}