package com.example.deckora.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.deckora.R
import com.example.deckora.data.model.api.CarpetaApi
import com.example.deckora.navigation.Screen
import com.example.deckora.viewmodel.CartaViewModel
import com.example.deckora.viewmodel.MainViewModel
import com.example.deckora.viewmodel.ResumenViewModel
import com.example.deckora.viewmodel.UsuarioViewModel
import java.io.File
import java.io.FileOutputStream
import kotlin.collections.isNotEmpty
import kotlin.collections.plus
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    navController: NavController,
    viewModel: MainViewModel,
    usuarioViewModel: UsuarioViewModel, //Obtener el usuario
    resumenViewModel: ResumenViewModel = viewModel(), //Cargar carpetas (Para poder elegir una en elcombobox)
    cartaViewModel: CartaViewModel = viewModel() ,//Usa la funcion de crear cartas
    isTest: Boolean = false
) {


    val context = LocalContext.current
    //Necesario para el resumen
    val usuarioId = usuarioViewModel.estado.collectAsState().value.id
    val carpetas = resumenViewModel.carpetasUsuario.collectAsState()

    // Necesarios para crear la carta en si
    var fotoSeleccionada by remember { mutableStateOf<Bitmap?>(null) }
    var nombre by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var carpetaSeleccionada by remember { mutableStateOf<Long?>(null) }
    var categoriaSeleccionada by remember { mutableStateOf(1) } // Magic default

    //Estado para el popup de crear carta
    var mostrarDialogo by remember { mutableStateOf(false) }

    // Cargar carpetas por el usuario id
    if (!isTest) {
        LaunchedEffect(usuarioId) {
            usuarioId?.let { resumenViewModel.cargarCarpetasUsuario(it) }
        }
    }


    // Launcher de galeria
    val selectImageLauncher = if (!isTest) {
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                val bmp = ImageDecoder.decodeBitmap(source)
                fotoSeleccionada = bmp
                mostrarDialogo = true
            }
        }
    } else null


    // Launcher camara
    val takePictureLauncher = if (!isTest) {
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bmp ->
            if (bmp != null) {
                fotoSeleccionada = bmp
                mostrarDialogo = true
            }
        }
    } else null


    val permissionsLauncher =
        if (!isTest) {
            rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) takePictureLauncher?.launch(null)
            }
        } else null




    //NAvbar
    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf(Screen.Home, Screen.Profile, Screen.Camera, Screen.Carpeta)
                    .forEachIndexed { index, screen ->
                        NavigationBarItem(
                            selected = index == 2,
                            onClick = { viewModel.navigateTo(screen) },
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
                                    contentDescription = null
                                )
                            }
                        )
                    }
            }
        }
    ) { padding ->
        //Pagina
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Separador morado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 35.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Subir una foto",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(Modifier.height(300.dp))
            Text("¡Agrega una nueva carta!")

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = {
                        if (!isTest) selectImageLauncher?.launch("image/*")},
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("Galería")
                }

                Button(
                    onClick = {
                        if (!isTest) {
                            val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                            if (permission == PackageManager.PERMISSION_GRANTED) {
                                takePictureLauncher?.launch(null)
                            } else {
                                permissionsLauncher?.launch(Manifest.permission.CAMERA)
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("Tomar Foto")
                }

            }
        }
    }


    // Popup
    if (mostrarDialogo && fotoSeleccionada != null) {

        AlertDialog(  // <- Popup
            onDismissRequest = { mostrarDialogo = false },

            title = { Text("Crear nueva carta") },

            text = {

                Column {

                    Image(
                        bitmap = fotoSeleccionada!!.asImageBitmap(), //<- Muestra una vista previa de la imagen
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(Modifier.height(10.dp))

                    TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                    Spacer(Modifier.height(8.dp))

                    TextField(value = estado, onValueChange = { estado = it }, label = { Text("Estado") })
                    Spacer(Modifier.height(8.dp))

                    TextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
                    Spacer(Modifier.height(16.dp))

                    Text("Carpeta:")
                    DropdownMenuCarpetas(
                        carpetas = carpetas.value,
                        carpetaSeleccionada = carpetaSeleccionada,
                        onSelect = { carpetaSeleccionada = it }
                    )

                    Spacer(Modifier.height(16.dp))

                    Text("Categoría:")
                    DropdownMenuCategorias(
                        categoriaSeleccionada = categoriaSeleccionada,
                        onSelect = { categoriaSeleccionada = it }
                    )
                }
            },

            //Si se apreta el boton, se envia el formulario de más abajo.
            confirmButton = {
                TextButton(onClick = {

                    if (usuarioId != null && carpetaSeleccionada != null) {

                        cartaViewModel.crearCartaConImagen(
                            context = context,
                            bitmap = fotoSeleccionada!!,
                            nombre = nombre,
                            estado = estado,
                            descripcion = descripcion,
                            carpetaId = carpetaSeleccionada!!,
                            usuarioId = usuarioId,
                            onSuccess = {
                                mostrarDialogo = false
                                Toast.makeText(context, "La carta se creó exitosamente!", Toast.LENGTH_SHORT).show()
                            },
                            onError = {
                                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                            }
                        )
                    }

                }) {
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
}

//Comboboxes
@Composable
fun DropdownMenuCarpetas(
    carpetas: List<CarpetaApi>,  //<- saca la lista de carpetas de la api para mostrarlas...
    carpetaSeleccionada: Long?,
    onSelect: (Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val nombreSeleccionado = carpetas
        .firstOrNull { it.id == carpetaSeleccionada }
        ?.nombre_carpeta ?: "Seleccionar carpeta"

    Box {

        Button(onClick = { expanded = true }) {
            Text(nombreSeleccionado)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            carpetas.forEach { carpeta ->
                DropdownMenuItem(
                    text = { Text(carpeta.nombre_carpeta ?: "Sin nombre") }, //<- Aqui. por el nombre
                    onClick = {
                        onSelect(carpeta.id!!) //<- Se manda el id.
                        expanded = false
                    }
                )
            }
        }
    }
}


//Hacemos algo similar aqui, solo que como eran solo dos categorias lo pusimos manual.
@Composable
fun DropdownMenuCategorias(
    categoriaSeleccionada: Int?,
    onSelect: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val categorias = listOf(
        1 to "Magic",
        2 to "Pokémon"
    )

    Box {
        Button(onClick = { expanded = true }) {
            Text(
                categorias.find { it.first == categoriaSeleccionada }?.second
                    ?: "Seleccionar categoría"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categorias.forEach { (id, nombre) ->
                DropdownMenuItem(
                    text = { Text(nombre) },
                    onClick = {
                        onSelect(id)
                        expanded = false
                    }
                )
            }
        }
    }
}




