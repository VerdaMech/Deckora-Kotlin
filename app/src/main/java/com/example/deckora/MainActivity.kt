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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.deckora.data.model.Carpeta
import com.example.deckora.navigation.NavigationEvent
import com.example.deckora.navigation.Screen
import com.example.deckora.ui.screens.CameraScreen
import com.example.deckora.ui.screens.CarpetaDetalleScreen
import com.example.deckora.ui.screens.HomeScreen
import com.example.deckora.ui.screens.LoginScreen
import com.example.deckora.ui.screens.ProfileScreen
import com.example.deckora.ui.screens.SignUpScreen
import com.example.deckora.ui.screens.CarpetaScreen
import com.example.deckora.viewmodel.MainViewModel
import com.example.deckora.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val usuarioViewModel: UsuarioViewModel = viewModel()
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
                            ProfileScreen(navController = navController, viewModel = viewModel, usuarioViewModel = usuarioViewModel)
                        }
                        composable(route = Screen.Camera.route){
                            CameraScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.SignUp.route){
                            SignUpScreen(navController = navController, viewModel = viewModel, usuarioViewModel = usuarioViewModel)
                        }
                        composable(route = Screen.Login.route){
                            LoginScreen(navController = navController, viewModel = viewModel, usuarioViewModel = usuarioViewModel)
                        }
                        composable(route = Screen.Carpeta.route){
                            CarpetaScreen(navController = navController, viewModel = viewModel, usuarioViewModel = usuarioViewModel)
                        }
                        composable(route = "carpeta_detalles/{idCarpeta}/{nombreCarpeta}", arguments = listOf(navArgument("idCarpeta") { type = NavType.LongType })) { backStackEntry ->
                            val idCarpeta = backStackEntry.arguments?.getLong("idCarpeta")!!
                            val nombreCarpeta = backStackEntry.arguments?.getString("nombreCarpeta")!!
                            CarpetaDetalleScreen(navController = navController,nombreCarpeta = nombreCarpeta, idCarpeta = idCarpeta, usuarioViewModel = usuarioViewModel, viewModel = viewModel
                            )
                        }

                    }

                }
            }
        }
    }


