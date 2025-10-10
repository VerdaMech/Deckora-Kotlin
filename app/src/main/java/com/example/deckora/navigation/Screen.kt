package com.example.deckora.navigation

sealed class Screen (val route:String){

    data object Home : Screen("home_page")

    data object Profile : Screen("profile_page")

    data object Settings : Screen("settings_page")

    data class Detail(val itemId: String): Screen("detail:page/{itemId}"){
        fun buildRoute(): String {
            return route.replace("{itemId}", itemId)
        }
    }
}