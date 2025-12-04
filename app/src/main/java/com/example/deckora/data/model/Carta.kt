package com.example.deckora.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Carta (
    @PrimaryKey (autoGenerate = true) var id: Int = 0,
    var nombre: String = "",
    var correo: String = "",
    var clave: String = ""
)