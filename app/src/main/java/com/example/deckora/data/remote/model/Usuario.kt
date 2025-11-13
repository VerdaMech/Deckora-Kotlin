package com.example.deckora.data.remote.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario (
    @PrimaryKey (autoGenerate = true) var id: Int = 0,
    var nombre: String = "",
    var correo: String = "",
    var clave: String = "",
    var repiteClave: String = "",
    @Ignore
    val errores : RegistroErrores = RegistroErrores(),
    @Ignore
    val mostrarErrores : Boolean = false,
    @Ignore
    val loginErrores : LoginErrores = LoginErrores(),
    @Ignore
    var estadoLogin : Boolean = false

)