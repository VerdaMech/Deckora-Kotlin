package com.example.deckora

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.example.deckora.ui.screens.LoginScreen
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyMainViewModel = FakeMainViewModel()
    private val dummyUsuarioViewModel = FakeUsuarioViewModel()

    @Test
    fun testVistaLoginScreen() {

        composeTestRule.setContent {
            val navController = rememberNavController()

            LoginScreen(
                navController = navController,
                viewModel = dummyMainViewModel,
                usuarioViewModel = dummyUsuarioViewModel
            )
        }

        // ----------- Texto principal -----------
        composeTestRule.onNodeWithText("¡Inicia Sesión!")
            .assertIsDisplayed()

        // ----------- Campos -----------
        composeTestRule.onNodeWithText("Nombre de Usuario")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Contraseña")
            .assertIsDisplayed()

        // ----------- Botón -----------
        composeTestRule.onNodeWithText("Iniciar Sesión")
            .assertIsDisplayed()

        // ----------- Barra inferior -----------
        composeTestRule.onNodeWithText("Cartas Top")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Tú Perfil")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Cámara")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Carpetas")
            .assertIsDisplayed()

        // ----------- Interacciones ----------
        composeTestRule.onNodeWithText("Nombre de Usuario")
            .performTextInput("Vicente")

        composeTestRule.onNodeWithText("Contraseña")
            .performTextInput("123456")

        // ----------- Confirmar que aparece lo ingresado ----------
        composeTestRule.onNodeWithText("Vicente")
            .assertIsDisplayed()
    }
}