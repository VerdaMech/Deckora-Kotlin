package com.example.deckora.navigation

sealed class Screen (val route:String){

    data object Home : Screen("Cartas Principales")

    data object Profile : Screen("Tú Perfil")

    data object SingUp : Screen("Registrarse")

    data object Settings : Screen("Configuraciones")

    data class Detail(val itemId: String): Screen("detail:page/{itemId}"){
        fun buildRoute(): String {
            return route.replace("{itemId}", itemId)
        }
    }
}