package com.example.deckora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deckora.data.model.api.CarpetaApi
import com.example.deckora.repository.CarpetaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CarpetaViewModel : ViewModel() {
    private val repo = CarpetaRepository()

    fun crearCarpeta(idUsuario: Long, nombre: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repo.crearCarpeta(idUsuario, nombre)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

