package com.example.deckora.viewmodel

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

class CartaViewModel : ViewModel() {

    private val resumenRepo = ResumenRepository()
    private val CartaRepo = CartaRepository()
    private val ImagenRepo = ImagenRepository()

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

    fun subirYCrearCarta(
        file: File,
        idUsuario: Long,
        idCarpeta: Long,
        carta: CartaApi,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val url = ImagenRepo.subirImagen(file)

                val cartaFinal = carta.copy(
                    imagen_url = url
                )

                CartaRepo.crearCarta(idUsuario, idCarpeta, cartaFinal)

                onSuccess()

            } catch (e: Exception) {
                onError(e.message ?: "Error inesperado")
            }
        }
    }
}

