package com.example.funciona.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsgapp.Producto
import com.example.tsgapp.getProductosAhorramas
import com.example.tsgapp.getProductosDIA
import com.example.tsgapp.getProductosMercadona
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {
    private val _selectedSupermarkets = MutableStateFlow<List<String>>(
        listOf("DIA", "Ahorramas", "Carrefour")
    )
    val selectedSupermarkets = _selectedSupermarkets.asStateFlow()

    fun updateSelectedSupermarkets(supermarkets: List<String>) {
        _selectedSupermarkets.value = supermarkets
    }
    var query by mutableStateOf("")
    var productosDIA by mutableStateOf<List<Producto>>(emptyList())
    var productosAhorramas by mutableStateOf<List<Producto>>(emptyList())
    var productosMercadona by mutableStateOf<List<Producto>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun updateQuery(newQuery: String) {
        query = newQuery
    }

    fun searchProducts() {
        errorMessage = null

        viewModelScope.launch {
            isLoading = true
            try {
                productosDIA = getProductosDIA(query)
                productosAhorramas = getProductosAhorramas(query)
                productosMercadona = getProductosMercadona(query)
            } catch (e: Exception) {
                errorMessage = "Error al buscar productos"
            } finally {
                isLoading = false
            }
        }
    }
}