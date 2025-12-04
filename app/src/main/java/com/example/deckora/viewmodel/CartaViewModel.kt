import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deckora.data.model.api.CartaApi
import com.example.deckora.repository.ResumenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartaViewModel : ViewModel() {

    private val repo = ResumenRepository()

    private val _cartas = MutableStateFlow<List<CartaApi>>(emptyList())
    val cartas: StateFlow<List<CartaApi>> = _cartas

    fun cargarCartas(idCarpeta: Long) {
        viewModelScope.launch {
            try {
                val resultado = repo.getCartasByCarpeta(idCarpeta)
                _cartas.value = resultado
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
