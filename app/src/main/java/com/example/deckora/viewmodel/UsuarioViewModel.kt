package com.example.deckora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.deckora.data.model.LoginErrores
import com.example.deckora.data.model.RegistroErrores
import com.example.deckora.data.model.api.UsuarioApi
import com.example.deckora.data.model.UsuarioState
import com.example.deckora.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel() : ViewModel() {

    private val repository = UserRepository()

    private val _estado = MutableStateFlow(UsuarioState())
    val estado: StateFlow<UsuarioState> = _estado

    // ───────────────────────────────────────────
    // Actualización de campos
    // ───────────────────────────────────────────

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun onRepiteClaveChange(valor: String) {
        _estado.update { it.copy(repiteClave = valor, errores = it.errores.copy(repiteClave = null)) }
    }

    // ───────────────────────────────────────────
    // Registrar usuario (POST a la API)
    // ───────────────────────────────────────────

    fun addUser() {
        if (!validarUsuario()) return

        viewModelScope.launch {

            val estadoActual = _estado.value

            val nuevoUsuarioApi = UsuarioApi(
                id = null,
                nombre_usuario = estadoActual.nombre,
                correo_usuario = estadoActual.correo,
                contrasenia_usuario = estadoActual.clave
            )

            try {
                val creado = repository.createUsuario(nuevoUsuarioApi)

                println("Usuario creado correctamente en la API → id=${creado.id}")

                limpiarEstado()

            } catch (e: Exception) {
                _estado.update {
                    it.copy(
                        mostrarErrores = true,
                        errores = RegistroErrores(
                            nombre = e.message,
                            correo = e.message,
                            clave = e.message,
                            repiteClave = e.message
                        )
                    )
                }
            }

        }
    }

    // ───────────────────────────────────────────
    // Reset de estado UI
    // ───────────────────────────────────────────

    fun limpiarEstado() {
        _estado.update {
            it.copy(
                nombre = "",
                correo = "",
                clave = "",
                repiteClave = "",
                errores = RegistroErrores(),
                mostrarErrores = false,
                loginErrores = LoginErrores(),
                estadoLogin = false
            )
        }
    }

    // ───────────────────────────────────────────
    // Login usando API (validación básica)
    // ───────────────────────────────────────────

    fun loginUsuario() {
        val estadoActual = _estado.value

        val erroresLogin = LoginErrores(
            nombre = if (estadoActual.nombre.isBlank()) "El nombre de usuario no puede estar vacío" else null,
            clave = if (estadoActual.clave.isBlank()) "La contraseña no puede estar vacía" else null
        )

        if (erroresLogin.nombre != null || erroresLogin.clave != null) {
            _estado.update {
                it.copy(loginErrores = erroresLogin, mostrarErrores = true, estadoLogin = false)
            }
            return
        }

        viewModelScope.launch {
            try {
                val usuario = repository.loginUsuario(estadoActual.nombre, estadoActual.clave)
                println("LOGIN RECIBIDO DESDE API → $usuario")

                _estado.update {
                    it.copy(
                        id = usuario.id,
                        nombre = usuario.nombre_usuario,
                        correo = usuario.correo_usuario,
                        estadoLogin = true,
                        mostrarErrores = false,
                        loginErrores = LoginErrores()
                    )
                }

            } catch (e: Exception) {
                _estado.update {
                    it.copy(
                        loginErrores = LoginErrores(
                            nombre = "Nombre o contraseña incorrectos",
                            clave = "Verifica tus datos"
                        ),
                        mostrarErrores = true,
                        estadoLogin = false
                    )
                }
            }
        }
    }

    // ───────────────────────────────────────────
    // Validación de registro
    // ───────────────────────────────────────────

    fun validarUsuario(): Boolean {

        val estadoActual = _estado.value

        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")

        val errores = RegistroErrores(
            nombre = when {
                estadoActual.nombre.isBlank() -> "Campo obligatorio"
                estadoActual.nombre.length < 6 -> "Debe tener al menos 6 caracteres"
                else -> null
            },
            correo = when {
                estadoActual.correo.isBlank() -> "Campo obligatorio"
                !emailRegex.matches(estadoActual.correo) -> "Correo inválido"
                else -> null
            },
            clave = when {
                estadoActual.clave.isBlank() -> "Campo obligatorio"
                estadoActual.clave.length < 6 -> "Debe tener al menos 6 caracteres"
                else -> null
            },
            repiteClave = when {
                estadoActual.repiteClave.isBlank() -> "Campo obligatorio"
                estadoActual.repiteClave != estadoActual.clave -> "Las contraseñas no coinciden"
                else -> null
            }
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.repiteClave
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores, mostrarErrores = true) }

        return !hayErrores
    }
}
