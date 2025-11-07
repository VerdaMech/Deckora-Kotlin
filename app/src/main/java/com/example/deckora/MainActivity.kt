package com.example.deckora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.deckora.navigation.NavigationEvent
import com.example.deckora.navigation.Screen
import com.example.deckora.ui.screens.HomeScreen
import com.example.deckora.ui.screens.ProfileScreen
import com.example.deckora.ui.screens.SettingsScreen
import com.example.deckora.ui.screens.SingUpScreen
import com.example.deckora.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val navController = rememberNavController()

            LaunchedEffect(key1 = Unit) {
                viewModel.navigationEvents.collectLatest { event->
                    when(event){
                        is NavigationEvent.NavigateTo -> {
                            navController.navigate(event.route.route){
                                event.popUpToRoute?.let {
                                    popUpTo(it.route) {
                                        inclusive = event.inclusive
                                    }
                                    launchSingleTop = event.singleTop
                                    restoreState = true
                                }
                            }
                        }
                        is NavigationEvent.PopBackStack -> navController.popBackStack()
                        is NavigationEvent.NavigateUp -> navController.navigateUp()
                    }
                }
            }
                Scaffold (
                    modifier = Modifier.fillMaxSize()
                ){innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable(route = Screen.Home.route){
                            HomeScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Profile.route){
                            ProfileScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Settings.route){
                            SettingsScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.SignUp.route){
                            SingUpScreen(navController = navController, viewModel = viewModel)
                        }
                    }

                }
            }
        }
    }


