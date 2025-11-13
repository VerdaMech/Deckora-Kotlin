package com.example.deckora.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.deckora.navigation.Screen
import com.example.deckora.viewmodel.MainViewModel
import com.example.deckora.R
import com.example.deckora.viewmodel.UsuarioViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel
) {
    // Obtenemos el estado del usuario desde el ViewModel
    val estado by usuarioViewModel.estado.collectAsState()

    // Lista de pantallas en la barra inferior
    val items = listOf(Screen.Home, Screen.Profile, Screen.Camera)
    var selectedItem by remember { mutableStateOf(1) }

    var photos by remember { mutableStateOf(listOf<Bitmap>()) }
    val context = LocalContext.current
    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            val bmp = ImageDecoder.decodeBitmap(source)
            photos = photos + bmp
        }
    }

    Scaffold(
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Imagen superior del perfil
            Image(
                painter = painterResource(id = R.drawable.discord),
                contentDescription = "Imagen de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(width = 3.dp, color = Color.Black, shape = CircleShape)
                    .clip(CircleShape)
                    .size(120.dp)
                    .then(
                        if (estado.estadoLogin) {
                            Modifier.clickable {
                                selectImageLauncher.launch("image/*")
                            }
                        } else {
                            Modifier
                        }
                    )
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("¡Bienvenido al perfil!")

            Spacer(modifier = Modifier.height(24.dp))

            // Si el usuario está logeado
            if (estado.estadoLogin) {
                Text(
                    text = "¡Hola ${estado.nombre}!",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Nombre de usuario: ${estado.nombre}")
                Text("Correo: ${estado.correo}")

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        usuarioViewModel.limpiarEstado()
                        viewModel.navigateTo(Screen.Profile)
                    }
                ) {
                    Text("Cerrar sesión")
                }

            } else {
                Button(
                    onClick = { viewModel.navigateTo(Screen.Login) }
                ) {
                    Text("Iniciar sesión")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.navigateTo(Screen.SignUp) }
                ) {
                    Text("Crear usuario")
                }
            }
        }
    }
}