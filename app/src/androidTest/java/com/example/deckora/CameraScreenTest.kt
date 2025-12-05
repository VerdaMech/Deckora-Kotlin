package com.example.deckora

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.deckora.ui.screens.CameraScreen
import com.example.deckora.viewmodel.MainViewModel
import org.junit.Rule
import org.junit.Test


class CameraScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyViewModel = MainViewModel()



    @Test
    fun testVistaCameraScreen() {


        composeTestRule.setContent {
            val navController = rememberNavController()
            CameraScreen(navController = navController,
                viewModel = dummyViewModel
            )
        }


        composeTestRule.onNodeWithText("¡Agrega una nueva foto a tu colección!")
            .assertIsDisplayed()


        composeTestRule.onNodeWithText("Agregar de galería")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Tomar Foto")
            .assertIsDisplayed()


        composeTestRule.onNodeWithText("home")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("profile")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("camera")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("carpeta")
            .assertIsDisplayed()


        composeTestRule.onNodeWithText("Agregar de galería")
            .performClick()

        composeTestRule.onNodeWithText("Tomar Foto")
            .performClick()
    }
}
