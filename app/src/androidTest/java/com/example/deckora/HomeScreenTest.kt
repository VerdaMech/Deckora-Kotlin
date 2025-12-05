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
import com.example.deckora.ui.screens.HomeScreen
import com.example.deckora.viewmodel.MainViewModel
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyMainViewModel = MainViewModel()

    @Test
    fun testVistaHomeScreen() {

        composeTestRule.setContent {
            val navController = rememberNavController()

            HomeScreen(
                navController = navController,
                viewModel = dummyMainViewModel
            )
        }

        // ----------- Título principal -----------
        composeTestRule.onNodeWithText("¡Top cartas seleccionadas!")
            .assertIsDisplayed()

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

        // 1. Charizard siempre visible
        composeTestRule.onNodeWithText("#1. Charizard")
            .assertIsDisplayed()

    }
}