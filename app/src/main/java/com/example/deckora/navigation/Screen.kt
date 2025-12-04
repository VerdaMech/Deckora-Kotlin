package com.example.deckora.navigation

sealed class Screen (val route:String){

    data object Home : Screen("Cartas Top")

    data object Profile : Screen("Tú Perfil")

    data object Camera : Screen("Cámara")

    data object SignUp : Screen("Registrarse")

    data object Login : Screen("Inicia Sesión")

    data object Carpeta : Screen("Carpetas")

    object CarpetaDetalle : Screen("carpeta_detalles/{idCarpeta}")

    fun carpetaDetalle(idCarpeta: Long) = "carpeta_detalles/$idCarpeta"


    data class Detail(val itemId: String): Screen("detail:page/{itemId}"){
        fun buildRoute(): String {
            return route.replace("{itemId}", itemId)
        }
    }
}