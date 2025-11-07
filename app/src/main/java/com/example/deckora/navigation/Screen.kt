package com.example.deckora.navigation

sealed class Screen (val route:String){

    data object Home : Screen("Cartas Principales")

    data object Profile : Screen("Tú Perfil")

    data object Settings : Screen("Configuracion")

    data object SignUp : Screen("Registrarse")

    data class Detail(val itemId: String): Screen("detail:page/{itemId}"){
        fun buildRoute(): String {
            return route.replace("{itemId}", itemId)
        }
    }
}