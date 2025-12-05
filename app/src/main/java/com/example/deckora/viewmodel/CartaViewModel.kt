package com.example.deckora.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deckora.data.model.api.CartaApi
import com.example.deckora.repository.CartaRepository
import com.example.deckora.repository.ImagenRepository
import com.example.deckora.repository.ResumenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


//Necesario para mandar la imagen con la api de imgBB
fun bitmapToFile(context: Context, bitmap: Bitmap): File {
    val file = File(context.cacheDir, "temp_${System.currentTimeMillis()}.jpg")
    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
    outputStream.flush()
    outputStream.close()
    return file
}

class CartaViewModel : ViewModel() {

    private val resumenRepo = ResumenRepository()
    private val cartaRepo = CartaRepository()
    private val imagenRepo = ImagenRepository()

    private val _cartas = MutableStateFlow<List<CartaApi>>(emptyList())
    val cartas: StateFlow<List<CartaApi>> = _cartas

    fun cargarCartas(idCarpeta: Long) {
        viewModelScope.launch {
            try {
                _cartas.value = resumenRepo.getCartasByCarpeta(idCarpeta)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun crearCartaConImagen(
        context: Context,
        bitmap: Bitmap,
        nombre: String,
        estado: String,
        descripcion: String,
        carpetaId: Long,
        usuarioId: Long,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {

        //Lo de imgBB
        try {
            // 1 → convertir bitmap a archivo temporal
            val file = bitmapToFile(context, bitmap)

            // 2 → subir imagen a ImgBB
            val imagenUrl = imagenRepo.subirImagenSuspend(file)

            // 3 → crear carta en la API
            cartaRepo.crearCarta(
                idUsuario = usuarioId,
                idCarpeta = carpetaId,
                nombre = nombre,
                estado = estado,
                descripcion = descripcion,
                imagenUrl = imagenUrl,
                categoriaId = 1
            )

            onSuccess()

        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.message ?: "Error desconocido")
        }
    }
}


