package com.example.deckora.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario (
    @PrimaryKey val id: Int = 1,
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
)