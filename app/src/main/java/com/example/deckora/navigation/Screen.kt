package com.example.deckora.navigation

import android.net.Uri

sealed class Screen (val route:String){

    data object Home : Screen("Cartas Top")

    data object Profile : Screen("Tú Perfil")

    data object Camera : Screen("Cámara")

    data object SignUp : Screen("Registrarse")

    data object Login : Screen("Inicia Sesión")

    data object Carpeta : Screen("Carpetas")

    object CarpetaDetalle : Screen("carpeta_detalles/{idCarpeta}/{nombreCarpeta}")


    fun carpetaDetalle(idCarpeta: Long, nombreCarpeta: String) =
        "carpeta_detalles/$idCarpeta/${Uri.encode(nombreCarpeta)}" //Se tiene que usar encode porque sino da error



    data class Detail(val itemId: String): Screen("detail:page/{itemId}"){
        fun buildRoute(): String {
            return route.replace("{itemId}", itemId)
        }
    }
}