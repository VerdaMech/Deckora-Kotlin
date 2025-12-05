package com.example.deckora


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.example.deckora.ui.screens.SignUpScreen
import com.example.deckora.viewmodel.MainViewModel
import com.example.deckora.viewmodel.UsuarioViewModel
import org.junit.Rule
import org.junit.Test


class SignUpScreenTest {


    @get:Rule
    val composeTestRule = createComposeRule()



    private val dummyMainViewModel = MainViewModel()
    private val dummyUsuarioViewModel = UsuarioViewModel()


    @Test
    fun testVistaSignUpScreen() {

        composeTestRule.setContent {
            val navController = rememberNavController()

            SignUpScreen(
                navController = navController,
                viewModel = dummyMainViewModel,
                usuarioViewModel = dummyUsuarioViewModel
            )
        }

        // ----------- Texto principal -----------
        composeTestRule.onNodeWithText("¡Crea tu usuario!")
            .assertIsDisplayed()

        // ----------- Campos -----------
        composeTestRule.onNodeWithText("Nombre de Usuario")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Correo")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Contraseña")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Repetir Contraseña")
            .assertIsDisplayed()

        // ----------- Botón -----------
        composeTestRule.onNodeWithText("Crear Usuario")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Crear Usuario")
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

        // ----------- Interacciones ----------
        composeTestRule.onNodeWithText("Nombre de Usuario")
            .performTextInput("Vicente")

        composeTestRule.onNodeWithText("Correo")
            .performTextInput("vicente@example.com")

        composeTestRule.onNodeWithText("Contraseña")
            .performTextInput("123456")

        composeTestRule.onNodeWithText("Repetir Contraseña")
            .performTextInput("123456")

        // ----------- Confirmar que aparece lo ingresado ----------
        composeTestRule.onNodeWithText("Vicente")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("vicente@example.com")
            .assertIsDisplayed()
    }
}