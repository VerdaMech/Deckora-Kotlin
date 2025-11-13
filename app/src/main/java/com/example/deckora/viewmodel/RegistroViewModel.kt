package com.example.deckora.viewmodel

import androidx.lifecycle.ViewModel
import com.example.deckora.model.UsuarioErrores
import com.example.deckora.model.UsuarioState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RegistroViewModel(): ViewModel() {

    private val _estado = MutableStateFlow(UsuarioState())

    val estado: StateFlow<UsuarioState> = _estado

    fun onNombreChange(valor : String){
        _estado.update { it.copy(nombre = valor , errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor : String){
        _estado.update { it.copy(correo = valor , errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor : String){
        _estado.update { it.copy(clave = valor , errores = it.errores.copy(clave = null)) }
    }


    fun validarFormulario() : Boolean{
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Campo obligatrio" else null,
            correo = if (estadoActual.correo.contains("@"))"Correo inválido" else null,
            clave = if (estadoActual.clave.length < 6)"Debe tener al menos 6 carácteres" else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave
        ).isNotEmpty()

        _estado.update{it.copy(errores = errores)}

        return !hayErrores
    }
}