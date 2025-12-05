package com.example.deckora

import com.example.deckora.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUsuarioViewModel : UsuarioViewModel() {
    override fun limpiarEstado() { /* no tocar estado */ }
    override fun validarUsuario() = true
    override fun addUser() { /* no agregar nada */ }
    private val _estadoFake = MutableStateFlow(
        EstadoLogin(
            estadoLogin = true,
            nombre = "Vicente",
            correo = "vicente@mail.com"
        )
    )

    override val estado = _estadoFake

    fun setLoggedOut() {
        _estadoFake.value = EstadoLogin()
    }
}