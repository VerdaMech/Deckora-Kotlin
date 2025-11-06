package com.example.deckora.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.deckora.navigation.Screen
import com.example.deckora.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.example.deckora.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel : MainViewModel = viewModel()
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val cardImages = listOf(
        R.drawable.charizard,
        R.drawable.jolteon,
        R.drawable.lucario
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú", modifier = Modifier.padding(16.dp))
                NavigationDrawerItem(
                    label = {Text("Ir a Perfil")},
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.navigateTo(Screen.Profile)
                    }
                )
                NavigationDrawerItem(
                    label = {Text("Registrarse")},
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.navigateTo(Screen.SingUp)
                    }
                )
                NavigationDrawerItem(
                    label = {Text("Configuración")},
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.navigateTo(Screen.Settings)
                    }
                )
            }
        }
    ){
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Cartas Principales") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch{
                                drawerState.open() }
                        }){
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            }
        ){ innerPadding ->
            LazyColumn (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp), // Añade padding horizontal para mejor estética
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre las "cartas"
            ){
                itemsIndexed(cardImages) { index, imageId -> // Usamos itemsIndexed para obtener el índice y el ID de la imagen
                    // --- Contenido de CADA CARTA ---
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        // AHORA CADA IMAGEN ES DINÁMICA DE LA LISTA
                        Image(
                            painter = painterResource(id = imageId), // Usa el 'imageId' de la lista
                            contentDescription = "Imagen de la carta ${index + 1}",
                            modifier = Modifier
                                .height(150.dp)
                                .padding(bottom = 8.dp)
                        )

                        Text("Aquí debo lograr poner una carta número ${index + 1}!")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.navigateTo(Screen.Settings) }) {
                            Text("Agregar al carrito")
                        }
                    }
                }
            }
        }
    }
}