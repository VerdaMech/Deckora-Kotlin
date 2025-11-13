package com.example.deckora.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.example.deckora.R
import com.example.deckora.navigation.Screen
import com.example.deckora.viewmodel.MainViewModel
import kotlin.collections.isNotEmpty
import kotlin.collections.plus

@Composable
fun CameraScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    // Lista de imagenes para la camara
    val imagenesCamara = listOf(
        R.drawable.m1,
        R.drawable.m2,
        R.drawable.m3,
        R.drawable.m4,
        R.drawable.m5,
        R.drawable.m6
    )

    //funcion camara
    var photos by remember { mutableStateOf(listOf<Bitmap>()) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    //Tomar foto con la camara (guarda una foto de la lista de arriba, pero sale el monito verde)
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {
        val randomImageRes = imagenesCamara.random() // selecciona una carta al azar
        val randomBitmap = BitmapFactory.decodeResource(context.resources, randomImageRes)

        photos = photos + randomBitmap
    }

    //Abrir camara
    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permiso concedido, lanza la cámara
            takePictureLauncher.launch(null)
        } else {
            // Permiso denegado
            Toast.makeText(context, "Se necesita permiso de camara para acceder a la funcion.", Toast.LENGTH_SHORT).show()
        }
    }



    //Abrir galería
    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            val bmp = ImageDecoder.decodeBitmap(source)
            photos = photos + bmp
        }
    }



    //Lista de las pantallas, de la parte de abajo
    val items = listOf(Screen.Home, Screen.Profile, Screen.Camera)
    //Marca una sombra en la opción seleccionada
    var selectedItem by remember { mutableStateOf(2) }

    //Barra de abajo
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
                                    else -> Icons.Default.Info
                                },
                                contentDescription = screen.route
                            )
                        }
                    )
                }
            }
        }
        //contenido pagina
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "¡Agrega una nueva foto a tu colección!")

            Spacer(modifier = Modifier.height(16.dp))

            //BOTONES
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 600.dp)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Boton galeria
                item {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(200.dp),
                        onClick = { selectImageLauncher.launch("image/*") }
                    ) {
                        Text("Agregar de galería")
                    }
                }

                // Boton camara
                item {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(200.dp),

                        onClick = {
                            val permissionCheckResult = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            )
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                takePictureLauncher.launch(null)
                            } else {
                                permissionsLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    ) {
                        Text("Tomar Foto")
                    }
                }
            }

            // Mostrar fotos abajo (Camara y galería)
            if (photos.isNotEmpty()){
                Text("")
                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 600.dp)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(photos.size) { index ->
                        Image(
                            bitmap = photos[index].asImageBitmap(),
                            contentDescription = "Foto ${index + 1}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(15.dp)),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}