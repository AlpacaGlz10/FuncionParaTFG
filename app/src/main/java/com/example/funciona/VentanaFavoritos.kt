package com.example.tsgapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.funciona.ui.theme.FavoritesRepository

class VentanaFavoritos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {

        }
    }
}

@Composable
fun Favoritos() {
    val context = LocalContext.current
    val favorites = FavoritesRepository.loadFavorites(context)

    val favoritosDIA = remember(favorites) {
        favorites.filter { it.supermercado == "DIA" }
    }
    val favoritosAhorramas = remember(favorites) {
        favorites.filter { it.supermercado == "Ahorramas" }
    }

    if (favorites.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No tienes productos favoritos")
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            if (favoritosDIA.isNotEmpty()) {
                Text(
                    text = "Favoritos de DIA",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(favoritosDIA.size) { index ->
                        MostrarProductoFavorito(producto = favoritosDIA[index], context = context)
                    }
                }
            }

            if (favoritosAhorramas.isNotEmpty()) {
                Text(
                    text = "Favoritos de Ahorramas",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(favoritosAhorramas.size) { index ->
                        MostrarProductoFavorito(producto = favoritosAhorramas[index], context = context)
                    }
                }
            }

            if (favoritosDIA.isEmpty() && favoritosAhorramas.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay favoritos de DIA ni Ahorramas")
                }
            }
        }
    }
}

@Composable
fun MostrarProductoFavorito(producto: Producto, context: Context) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(producto.imagenUrl)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text(producto.nombre, fontWeight = FontWeight.Bold)
                Text("Precio: ${producto.precio}")
            }
            // Botón para eliminar
            Button(
                onClick = { FavoritesRepository.toggleFavorite(context, producto) },
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text("Eliminar")
            }
        }
    }
}
