package com.example.deckora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.deckora.data.remote.dao.UsuarioDao

class UsuarioViewModelFactory(private val dao: UsuarioDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioViewModel::class.java)){
             @Suppress("UNCHECKED_CAST")
             return UsuarioViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}