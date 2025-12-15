package com.example.deckora
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.navigation.compose.rememberNavController
import com.example.deckora.ui.screens.CarpetaScreen
import com.example.deckora.ui.screens.HomeScreen
import com.example.deckora.viewmodel.CarpetaViewModel
import com.example.deckora.viewmodel.MainViewModel
import com.example.deckora.viewmodel.ResumenViewModel
import com.example.deckora.viewmodel.UsuarioViewModel
import org.junit.Rule
import org.junit.Test

class CarpetaScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyMainViewModel = MainViewModel()
    private val dummyResumenViewModel = ResumenViewModel()
    private val dummyUsuarioViewModel = UsuarioViewModel()
    private val dummyCarpetaViewModel = CarpetaViewModel()


    @Test
    fun testVistaCarpetaScreen() {

        composeTestRule.setContent {
            val navController = rememberNavController()

            CarpetaScreen(
                navController = navController,
                viewModel = dummyMainViewModel,
                usuarioViewModel = dummyUsuarioViewModel,
                resumenViewModel = dummyResumenViewModel,
                carpetaViewModel = dummyCarpetaViewModel
            )
        }

        // ----------- Título principal -----------
        composeTestRule.onNodeWithText("Mis carpetas")
            .assertIsDisplayed()
        //
        composeTestRule.onNodeWithContentDescription("Crear carpeta")
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