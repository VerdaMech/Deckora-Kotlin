package com.example.deckora

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import androidx.navigation.compose.rememberNavController
import com.example.deckora.ui.screens.HomeScreen
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyMainViewModel = FakeMainViewModel()

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

        composeTestRule.onNodeWithText("Tú Perfil")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Cámara")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Carpetas")
            .assertIsDisplayed()

        // ----------- Cartas (LazyColumn) -----------
        val lazyColumn = composeTestRule.onNode(hasScrollAction())

        // 1. Charizard siempre visible
        composeTestRule.onNodeWithText("#1. Charizard")
            .assertIsDisplayed()

        // 2. Jolteon → scroll antes de buscarlo
        lazyColumn.performScrollToNode(hasText("#2. Jolteon"))
        composeTestRule.onNodeWithText("#2. Jolteon")
            .assertIsDisplayed()

        // 3. Lucario
        lazyColumn.performScrollToNode(hasText("#3. Lucario"))
        composeTestRule.onNodeWithText("#3. Lucario")
            .assertIsDisplayed()

        // 4. Carta Magic
        lazyColumn.performScrollToNode(hasText("4# Carta magic"))
        composeTestRule.onNodeWithText("4# Carta magic")
            .assertIsDisplayed()

        // ----------- Imágenes ----------

        composeTestRule.onNodeWithContentDescription("Imagen de la carta 1")
            .assertExists()

        composeTestRule.onNodeWithContentDescription("Imagen de la carta 2")
            .assertExists()

        composeTestRule.onNodeWithContentDescription("Imagen de la carta 3")
            .assertExists()

        composeTestRule.onNodeWithContentDescription("Imagen de la carta 4")
            .assertExists()
    }
}