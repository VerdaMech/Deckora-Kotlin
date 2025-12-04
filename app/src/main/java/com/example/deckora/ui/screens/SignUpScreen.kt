package com.example.deckora.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.deckora.R
import com.example.deckora.navigation.Screen
import com.example.deckora.viewmodel.MainViewModel
import com.example.deckora.viewmodel.UsuarioViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel (),
    usuarioViewModel: UsuarioViewModel
){

    //Funcion dentro del viewModel para limpiar
    //los estados de los input a sus valores por defecto
    usuarioViewModel.limpiarEstado()


    //Lista de las pantallas, de la parte de abajo
    val items = listOf(Screen.Home, Screen.Profile, Screen.Camera, Screen.Carpeta)
    //Marca una sombra en la opción seleccionada
    var selectedItem by remember { mutableStateOf(1) }

    val estado by usuarioViewModel.estado.collectAsState()


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
                                    Screen.Camera -> Icons.Default.CameraAlt
                                    Screen.Profile -> Icons.Default.Person
                                    Screen.Carpeta -> Icons.Default.Photo
                                    else -> Icons.Default.Info
                                },
                                contentDescription = screen.route
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding -> //Contenido de la pantalla
                        //Imagen de usuario, campos de texto y boton
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.discord),
                contentDescription = "Imagen de la carta",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(width = 3.dp, color = Color.Black, shape = CircleShape)
                    .clip(CircleShape)
                    .size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("¡Crea tu usuario!")
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = usuarioViewModel::onNombreChange,
                label = { Text("Nombre de Usuario") },
                isError = estado.mostrarErrores && estado.errores.nombre != null,
                supportingText = {
                    if (estado.mostrarErrores) {
                        estado.errores.nombre?.let {
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 2.dp)
            )


            OutlinedTextField(
                value = estado.correo,
                onValueChange = usuarioViewModel::onCorreoChange,
                label = { Text("Correo") },
                isError = estado.mostrarErrores && estado.errores.correo != null,
                supportingText = {
                    if (estado.mostrarErrores) {
                        estado.errores.correo?.let {
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 2.dp)
            )


            OutlinedTextField(
                value = estado.clave,
                onValueChange = usuarioViewModel::onClaveChange,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.mostrarErrores && estado.errores.clave != null,
                supportingText = {
                    if (estado.mostrarErrores) {
                        estado.errores.clave?.let {
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 2.dp)
            )

            // 4. Repetir Contraseña
            OutlinedTextField(
                value = estado.repiteClave,
                onValueChange = usuarioViewModel::onRepiteClaveChange,
                label = { Text("Repetir Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.mostrarErrores && estado.errores.repiteClave!= null,
                supportingText = {
                    if (estado.mostrarErrores) {
                        estado.errores.repiteClave?.let {
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 2.dp)
            )

            Button( modifier = Modifier
                .width(200.dp),
                onClick = {
                if (usuarioViewModel.validarUsuario()) {
                    usuarioViewModel.addUser()
                    println("Usuario agregado")
                    viewModel.navigateTo(Screen.Profile)
                } else {
                    println("usuario no agregado")
                }
            }) {
                Text("Crear Usuario")
            }

        }
    }
}
