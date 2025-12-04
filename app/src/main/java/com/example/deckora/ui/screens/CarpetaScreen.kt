package com.example.deckora.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.deckora.data.model.Carpeta
import com.example.deckora.data.model.api.CarpetaApi
import com.example.deckora.navigation.Screen
import com.example.deckora.viewmodel.CarpetaViewModel
import com.example.deckora.viewmodel.MainViewModel
import com.example.deckora.viewmodel.ResumenViewModel
import com.example.deckora.viewmodel.UsuarioViewModel

//Item de las carpetas
@Composable
fun CarpetaItem(
    carpeta: CarpetaApi,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Folder,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            carpeta.nombre_carpeta ?: "Sin nombre",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

//Pagina
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarpetaScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    resumenViewModel: ResumenViewModel = viewModel(),
    carpetaViewModel: CarpetaViewModel = viewModel(),
    viewModel: MainViewModel = viewModel()
) {
    val usuarioId = usuarioViewModel.estado.collectAsState().value.id
    val carpetas = resumenViewModel.carpetasUsuario.collectAsState()

    var mostrarDialogo by remember { mutableStateOf(false) }
    var nombreCarpeta by remember { mutableStateOf("") }

    val items = listOf(Screen.Home, Screen.Profile, Screen.Camera, Screen.Carpeta)
    var selectedItem by remember { mutableStateOf(3) }

    // Cargar carpetas al entrar
    LaunchedEffect(usuarioId) {
        if (usuarioId != null) {
            resumenViewModel.cargarCarpetasUsuario(usuarioId)
        }
    }

    // ---------- UI ----------
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarDialogo = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Crear carpeta",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        bottomBar = {
            NavigationBar {
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
    ) { paddingValues ->


        // ---------- POPUP CREAR CARPETA ----------
        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },

                title = {
                    Text("Crear nueva carpeta")
                },

                text = {
                    Column {
                        Text("Ingresa el nombre de la carpeta:")
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = nombreCarpeta,
                            onValueChange = { nombreCarpeta = it },
                            singleLine = true,
                            placeholder = { Text("Ej: Mis cartas legendarias") }
                        )
                    }
                },

                confirmButton = {
                    TextButton(
                        onClick = {
                            if (usuarioId != null && nombreCarpeta.isNotBlank()) {
                                carpetaViewModel.crearCarpeta(
                                    idUsuario = usuarioId,
                                    nombre = nombreCarpeta,
                                    onSuccess = {
                                        mostrarDialogo = false
                                        nombreCarpeta = ""
                                        resumenViewModel.cargarCarpetasUsuario(usuarioId)
                                    }
                                )
                            }
                        }
                    ) {
                        Text("Crear")
                    }
                },

                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // ---------- CONTENIDO PRINCIPAL ----------
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 35.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Mis carpetas",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            if (carpetas.value.isEmpty()) {
                Text("No tienes carpetas todavía")
                return@Column
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(36.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(carpetas.value) { carpeta ->
                    CarpetaItem(
                        carpeta = carpeta,
                        onClick = {
                            navController.navigate(
                                Screen.CarpetaDetalle.carpetaDetalle(
                                    idCarpeta = carpeta.id!!,
                                    nombreCarpeta = carpeta.nombre_carpeta ?: "Sin nombre"
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}




