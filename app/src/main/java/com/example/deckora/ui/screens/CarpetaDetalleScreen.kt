package com.example.deckora.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.deckora.data.model.api.CartaApi
import com.example.deckora.viewmodel.MainViewModel
import com.example.deckora.viewmodel.CartaViewModel
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.deckora.navigation.Screen
import com.example.deckora.viewmodel.UsuarioViewModel


@Composable
fun CartaItemGrid(carta: CartaApi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEFEFEF))
            .padding(5.dp)
    ) {

        carta.imagen_url?.let { url ->
            AsyncImage(
                model = carta.imagen_url,
                contentDescription = carta.nombre_carta,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.FillWidth
            )


            Spacer(Modifier.height(8.dp))
        }
    }
}


@Composable
fun CarpetaDetalleScreen(
    navController: NavController,
    idCarpeta: Long,
    nombreCarpeta: String,
    usuarioViewModel: UsuarioViewModel = viewModel(),
    viewModel: MainViewModel = viewModel(),
    cartaViewModel: CartaViewModel = viewModel(),

) {
    val cartas = cartaViewModel.cartas.collectAsState()
    val items = listOf(Screen.Home, Screen.Profile, Screen.Camera, Screen.Carpeta)
    //Marca una sombra en la opción seleccionada
    var selectedItem by remember { mutableStateOf(3) }


    //Barrita abajo
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

        // Cargar cartas cuando se abre la pantalla
        LaunchedEffect(idCarpeta) {
            cartaViewModel.cargarCartas(idCarpeta)
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            // Barra morada
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 35.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = nombreCarpeta,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }


            if (cartas.value.isEmpty()) {
                Text("No hay cartas en esta carpeta.")
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(cartas.value) { carta ->
                        CartaItemGrid(carta)
                    }
                }
            }
        }
    }
}


