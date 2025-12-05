package com.example.deckora

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.deckora.ui.screens.ProfileScreen
import com.example.deckora.viewmodel.MainViewModel
import com.example.deckora.viewmodel.UsuarioViewModel
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyMainViewModel = MainViewModel()
    private val dummyUsuarioViewModel = UsuarioViewModel()

    @Test
    fun testVistaProfileScreen() {

        composeTestRule.setContent {
            val navController = rememberNavController()

            ProfileScreen(
                navController = navController,
                viewModel = dummyMainViewModel,
                usuarioViewModel = dummyUsuarioViewModel
            )
        }

        // ----------- Texto principal -----------
        composeTestRule.onNodeWithText("¡Bienvenido al perfil!")
            .assertIsDisplayed()

        // ----------- Botón -----------
        composeTestRule.onNodeWithText("Iniciar sesión")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Crear usuario")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Iniciar sesión")
            .performClick()

        composeTestRule.onNodeWithText("Crear usuario")
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