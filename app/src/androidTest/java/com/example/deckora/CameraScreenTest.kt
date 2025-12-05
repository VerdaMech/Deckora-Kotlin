package com.example.deckora

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.deckora.ui.screens.CameraScreen
import com.example.deckora.viewmodel.CartaViewModel
import com.example.deckora.viewmodel.MainViewModel
import com.example.deckora.viewmodel.ResumenViewModel
import com.example.deckora.viewmodel.UsuarioViewModel
import org.junit.Rule
import org.junit.Test

class CameraScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyMainViewModel = MainViewModel()

    private val dummyCartaViewModel = CartaViewModel()

    private val dummyResumenViewModel = ResumenViewModel()

    private val dummyUsuarioViewModel = UsuarioViewModel()


    @Test
    fun testVistaCameraScreen() {

        composeTestRule.setContent {
            val navController = rememberNavController()

            CameraScreen(
                navController = navController,
                viewModel = dummyMainViewModel,
                usuarioViewModel = dummyUsuarioViewModel,
                cartaViewModel = dummyCartaViewModel,
                resumenViewModel = dummyResumenViewModel
            )
        }

        // ----------- Texto principal -----------
        composeTestRule.onNodeWithText("Subir una foto")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("¡Agrega una nueva carta!")
            .assertIsDisplayed()

        // ----------- Botón -----------
        composeTestRule.onNodeWithText("Galería")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Galería")
            .performClick()

        composeTestRule.onNodeWithText("Tomar Foto")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Tomar Foto")
            .performClick()

        // ----------- Barra inferior -----------
        composeTestRule.onNodeWithText("Cartas Top")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Cartas Top")
            .performClick()

        composeTestRule.onNodeWithText("Tú Perfil")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Tú Perfil")
            .performClick()

        composeTestRule.onNodeWithText("Cámara")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Cámara")
            .performClick()

        composeTestRule.onNodeWithText("Carpetas")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Carpetas")
            .performClick()

    }
}