package com.example.deckora.data.remote.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deckora.data.remote.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(usuario: Usuario)

    @Query("SELECT * FROM usuario ORDER BY id DESC")
    fun getAllUsers(): Flow<List<Usuario>>

    @Delete
    suspend fun deleteUser(usuario: Usuario)
}