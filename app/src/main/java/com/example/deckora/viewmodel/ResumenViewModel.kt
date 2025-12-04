package com.example.deckora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deckora.data.model.api.CarpetaApi
import com.example.deckora.repository.ResumenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResumenViewModel : ViewModel() {
    private val resumenRepo = ResumenRepository()

    private val _carpetasUsuario = MutableStateFlow<List<CarpetaApi>>(emptyList())
    val carpetasUsuario: StateFlow<List<CarpetaApi>> = _carpetasUsuario

    fun cargarCarpetasUsuario(idUsuario: Long) {
        viewModelScope.launch {
            try {
                val resultado = resumenRepo.getCarpetasByUsuario(idUsuario)
                _carpetasUsuario.value = resultado
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}