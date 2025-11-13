package com.example.deckora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deckora.data.remote.dao.UsuarioDao
import com.example.deckora.data.remote.model.LoginErrores
import com.example.deckora.data.remote.model.Usuario
import com.example.deckora.data.remote.model.RegistroErrores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(private val dao: UsuarioDao) : ViewModel() {

    private val _estado = MutableStateFlow(Usuario())

    val estado: StateFlow<Usuario> = _estado

    fun onNombreChange(valor : String){
        _estado.update { it.copy(nombre = valor , errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor : String){
        _estado.update { it.copy(correo = valor , errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor : String){
        _estado.update { it.copy(clave = valor , errores = it.errores.copy(clave = null)) }
    }

    fun onRepiteClaveChange(valor : String){
        _estado.update { it.copy(repiteClave = valor , errores = it.errores.copy(repiteClave = null)) }
    }

    val usuarios = dao.getAllUsers()
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList())

    //Función para añadir usuario
    //Omitiendo los parametros que se declaran con @Ignore
    //Guardando solo las cosas necesarias
    fun addUser(){
        viewModelScope.launch {
            val estadoActual = _estado.value
            val nuevoUsuario = Usuario(
                nombre = estadoActual.nombre,
                correo = estadoActual.correo,
                clave = estadoActual.clave
            )
            dao.addUser(nuevoUsuario)

            limpiarEstado()
        }
    }

    //Funcion para reestablecer los estados
    //Normalmente la estamos aplicando una vez
    //Logeados o cuando registramos un usuario
    //o al inicio de las pantallas para resetear los errores
    //Cuando cambiamos entre ellas
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

    fun deleteUser(usuario: Usuario){
        viewModelScope.launch {
            dao.deleteUser(usuario)
        }
    }

    fun validarUsuario(): Boolean{

        val estadoActual = _estado.value

        val errores = RegistroErrores(
            nombre = when {
                estadoActual.nombre.isBlank() -> "Campo obligatorio"
                estadoActual.nombre.length < 6 -> "El nombre de usuario debe ser mayor a 6 caracteres"
                else -> null
            },
            correo = when {
                estadoActual.correo.isBlank() -> "Campo obligatorio"
                !estadoActual.correo.contains(other = "@") -> "Correo inválido, debe contener @direcciónDeSuCorreo" // 2. Formato incorrecto
                else -> null
            },
            clave = when {
                estadoActual.clave.isBlank() -> "Campo obligatorio"
                estadoActual.clave.length < 6 -> "Debe tener al menos 6 caracteres"
                else -> null
            },
            repiteClave = when {
                estadoActual.repiteClave.isBlank() -> "Campo obligatorio"
                estadoActual.repiteClave.length < 6 -> "Debe tener al menos 6 caracteres"
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

        _estado.update{it.copy(errores = errores, mostrarErrores = true)}

        return !hayErrores
    }

    fun loginUsuario() {
        val estadoActual = _estado.value

        val erroresLogin = LoginErrores(
            nombre = when {
                estadoActual.nombre.isBlank() -> "El nombre de usuario no puede estar vacío"
                else -> null
            },
            clave = when {
                estadoActual.clave.isBlank() -> "La contraseña no puede estar vacía"
                else -> null
            }
        )

        val hayErrores = listOfNotNull(
            erroresLogin.nombre,
            erroresLogin.clave,
        ).isNotEmpty()

        if (hayErrores) {
            _estado.update{it.copy(loginErrores = erroresLogin, mostrarErrores = true)}
            return
        }

        viewModelScope.launch {

            _estado.update { it.copy(mostrarErrores = false, loginErrores = LoginErrores()) }

            val usuarioEncontrado = dao.loginUser(
                nombre = estadoActual.nombre,
                clave = estadoActual.clave
            )

            if (usuarioEncontrado != null) {
                println("Bienvenido ${usuarioEncontrado.nombre}")
                estadoActual.estadoLogin = true
                limpiarEstado()

            } else {

                _estado.update {
                    it.copy(
                       loginErrores = LoginErrores(
                            nombre = "Nombre de usuario incorrecto",
                            clave = "Contraseña incorrecta"
                        ),
                        mostrarErrores = true
                    )
                }
            }
        }
    }

}