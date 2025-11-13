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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.deckora.navigation.Screen
import com.example.deckora.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.example.deckora.R.drawable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel : MainViewModel = viewModel()
){


    //Imagenes de las cartas más famosas
    val cardImages = listOf(
        drawable.charizard,
        drawable.jolteon,
        drawable.lucario,
    )

    //Lista de las pantallas, de la parte de abajo
    val items = listOf(Screen.Home, Screen.Profile, Screen.Camera)
    //Marca una sombra en la opción seleccionada
    var selectedItem by remember { mutableStateOf(0) }

    //Barra de abajo
        Scaffold(
            bottomBar = {
                NavigationBar{
                    items.forEachIndexed { index, screen ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                viewModel.navigateTo(screen)
                            },
                            label = { Text(screen.route) },
                            icon = {
                                Icon(
                                    imageVector = when (screen) {
                                        Screen.Home -> Icons.Default.Home
                                        Screen.Camera -> Icons.Default.AddCircle
                                        Screen.Profile -> Icons.Default.Person
                                        else -> Icons.Default.Info
                                    },
                                    contentDescription = screen.route
                                )
                            }
                        )
                    }
                }
            }
        ){ innerPadding ->//Contenido de la pantalla
                        //Imagenes de las cartas con su descripcion
            LazyColumn (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                itemsIndexed(cardImages) { index, imageId ->

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Image(
                            painter = painterResource(id = imageId),
                            contentDescription = "Imagen de la carta ${index + 1}",
                            modifier = Modifier
                                .height(190.dp)
                                .padding(bottom = 8.dp)
                        )

                        Text("Aquí debo lograr poner una carta número ${index + 1}!")
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
