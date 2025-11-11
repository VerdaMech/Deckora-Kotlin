package com.example.deckora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deckora.data.remote.dao.UsuarioDao
import com.example.deckora.data.remote.model.Usuario
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UsuarioViewModel(private val dao: UsuarioDao) : ViewModel() {

    val usuarios = dao.getAllUsers()
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList())

    fun addUser(nombre: String, correo: String, clave: String){
        viewModelScope.launch {
            dao.addUser(Usuario(nombre = nombre, correo = correo, clave = clave))
        }
    }

    fun deleteUser(usuario: Usuario){
        viewModelScope.launch {
            dao.deleteUser(usuario)
        }
    }

}