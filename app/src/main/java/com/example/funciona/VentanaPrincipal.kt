package com.example.tsgapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.funciona.R
import com.example.funciona.ui.theme.FavoritesRepository
import com.example.funciona.ui.theme.ProductsViewModel
import com.example.funciona.ui.theme.TamañoLetra
import com.example.funciona.ui.theme.ThemeState
import kotlinx.coroutines.launch

class VentanaPrincipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            principal()
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun principal(): String {
    val viewModel: ProductsViewModel = viewModel(
        viewModelStoreOwner = LocalContext.current as ViewModelStoreOwner
    )
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Recoger las tiendas seleccionadas del ViewModel
    val selectedSupermarkets by viewModel.selectedSupermarkets.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    leadingIcon = {
                        Image(
                            painter = painterResource(R.drawable.search),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    query = viewModel.query,
                    onQueryChange = { viewModel.updateQuery(it) },
                    onSearch = {
                        coroutineScope.launch {
                            viewModel.searchProducts()
                        }
                    },
                    active = false,
                    onActiveChange = {},
                    placeholder = { Text("Buscar...") },
                    modifier = Modifier
                        .weight(1f),
                    enabled = true,
                    shape = MaterialTheme.shapes.medium,
                    colors = SearchBarDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    content = {}
                )
                if (!ThemeState.isDarkMode) {
                    Image(
                        modifier = Modifier.size(80.dp).padding(top = 25.dp),
                        painter = painterResource(R.drawable.smartprice),
                        contentDescription = null
                    )
                } else {
                    Image(
                        modifier = Modifier.size(80.dp).padding(top = 25.dp),
                        painter = painterResource(R.drawable.smartpricedark),
                        contentDescription = null
                    )
                }
            }
            if (!ThemeState.isDarkMode) {
                Divider(modifier = Modifier.padding(top = 16.dp), color = Color.Black)
            } else {
                Divider(modifier = Modifier.padding(top = 16.dp), color = Color.White)
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                item {
                    if (viewModel.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else if (viewModel.errorMessage != null) {
                        Text(
                            text = "Error: ${viewModel.errorMessage}",
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        val hasProducts =
                            (selectedSupermarkets.contains("DIA") && viewModel.productosDIA.isNotEmpty()) ||
                                    (selectedSupermarkets.contains("Ahorramas") && viewModel.productosAhorramas.isNotEmpty()) ||
                                    (selectedSupermarkets.contains("Carrefour") && viewModel.productosMercadona.isNotEmpty())
                        if (hasProducts) {
                            Column {
                                if (selectedSupermarkets.contains("DIA")) {
                                    if (viewModel.productosDIA.isNotEmpty()) {
                                        HeaderSupermercado("Productos DIA") {
                                            if (!ThemeState.isDarkMode) {
                                                Button(
                                                    modifier = Modifier.size(80.dp),
                                                    onClick = {
                                                        Toast.makeText(
                                                            context,
                                                            "Puede variar el precio en tienda",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    },
                                                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                                                ) {
                                                    Image(
                                                        painter = painterResource(R.drawable.alert),
                                                        contentDescription = null
                                                    )
                                                }
                                            } else {
                                                Button(
                                                    modifier = Modifier.size(80.dp),
                                                    onClick = {
                                                        Toast.makeText(
                                                            context,
                                                            "Puede variar el precio en tienda",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    },
                                                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                                                ) {
                                                    Image(
                                                        painter = painterResource(R.drawable.alertdark),
                                                        contentDescription = null
                                                    )
                                                }
                                            }
                                        }
                                        ListaProductos(viewModel.productosDIA)
                                    } else {
                                        MensajeSinResultados("Productos DIA")
                                    }
                                }
                                if (selectedSupermarkets.contains("Ahorramas")) {
                                    if (viewModel.productosAhorramas.isNotEmpty()) {
                                        HeaderSupermercado("Productos Ahorramas") {}
                                        ListaProductos(viewModel.productosAhorramas)
                                    } else {
                                        MensajeSinResultados("Productos Ahorramas")
                                    }
                                }
                                if (selectedSupermarkets.contains("Carrefour")) {
                                    if (viewModel.productosMercadona.isNotEmpty()) {
                                        HeaderSupermercado("Productos Carrefour") {}
                                        ListaProductos(viewModel.productosMercadona)
                                    } else {
                                        MensajeSinResultados("Productos Carrefour")
                                    }
                                }
                            }
                        } else {
                            ProductosInicio()
                        }
                    }
                }
            }
        }
    }
    return viewModel.query
}

@Composable
fun HeaderSupermercado(
    titulo: String,
    trailingContent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = TamañoLetra.tamañoFuente.sp
            )
        }
        trailingContent()
    }
}

@Composable
fun ListaProductos(productos: List<Producto>) {
    val context = LocalContext.current
    LazyRow(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(productos.size) { index ->
            ProductoItem(producto = productos[index], context = context)
        }
    }
}

@Composable
fun MensajeSinResultados(nombreSupermercado: String) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = nombreSupermercado,
                fontWeight = FontWeight.Bold,
                fontSize = TamañoLetra.tamañoFuente.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "No se ha encontrado resultados en este supermercado",
            fontSize = TamañoLetra.tamañoFuente.sp
        )
    }
}

@Composable
fun ProductosInicio() {
    val viewModel: ProductsViewModel = viewModel()
    val selectedSupermarkets by viewModel.selectedSupermarkets.collectAsState()
    val contexto = LocalContext.current

    var productosDIA by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var productosAhorramas by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var productosMercadona by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val listacomienzo = listOf("Mazana", "Bolsa", "Yogurt", "Galletas", "Carne", "Refresco")
    val productoAleatorio = listacomienzo.random()

    LaunchedEffect(selectedSupermarkets) {
        isLoading = true
        if (selectedSupermarkets.contains("DIA")) {
            productosDIA = getProductosDIA(productoAleatorio)
        }
        if (selectedSupermarkets.contains("Ahorramas")) {
            productosAhorramas = getProductosAhorramas(productoAleatorio)
        }
        if (selectedSupermarkets.contains("Mercadona")) {
            productosMercadona = getProductosMercadona(productoAleatorio)
        }
        isLoading = false
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column {
            // Mostrar solo si la tienda está seleccionada
            if (selectedSupermarkets.contains("DIA") && productosDIA.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Productos DIA", fontWeight = FontWeight.Bold, fontSize = TamañoLetra.tamañoFuente.sp)
                }
                LazyRow(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(productosDIA.size) { index ->
                        ProductoItem(producto = productosDIA[index], context = contexto)
                    }
                }
            }

            if (selectedSupermarkets.contains("Ahorramas") && productosAhorramas.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Productos Ahorramas", fontWeight = FontWeight.Bold, fontSize = TamañoLetra.tamañoFuente.sp)
                }
                LazyRow(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(productosAhorramas.size) { index ->
                        ProductoItem(producto = productosAhorramas[index], context = contexto)
                    }
                }
            }

            if (selectedSupermarkets.contains("Carrefour") && productosMercadona.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Productos Carrefour", fontWeight = FontWeight.Bold, fontSize = TamañoLetra.tamañoFuente.sp)
                }
                LazyRow(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(productosMercadona.size) { index ->
                        ProductoItem(producto = productosMercadona[index], context = contexto)
                    }
                }
            }
        }
    }
}

@Composable
fun MarcarFavo(producto: Producto, onClick: (Producto) -> Unit) {
    val context = LocalContext.current
    var isFavorite by remember {
        mutableStateOf(FavoritesRepository.loadFavorites(context).contains(producto))
    }

    if (!ThemeState.isDarkMode){
        Button(
            onClick = {
                onClick(producto)
                isFavorite = !isFavorite
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Image(
                painter = painterResource(
                    id = if (isFavorite) R.drawable.favoritepselect else R.drawable.favoritep
                ),
                contentDescription = "Favorito",
                modifier = Modifier.size(20.dp)
            )
        }
    } else {
        Button(
            onClick = {
                onClick(producto)
                isFavorite = !isFavorite
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Image(
                painter = painterResource(
                    id = if (isFavorite) R.drawable.favoritepdarkselect else R.drawable.favoritepdark
                ),
                contentDescription = "Favorito",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ProductoItem(producto: Producto, context: Context) {
    var expandido by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { expandido = !expandido },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(producto.imagenUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            if (expandido) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = producto.nombre,
                    fontSize = TamañoLetra.tamañoFuente.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = producto.nombre,
                    fontSize = TamañoLetra.tamañoFuente.sp,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (producto.ofertaprecio.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = producto.ofertaprecio,
                    fontSize = TamañoLetra.tamañoFuente.sp
                )
            }
            if (producto.ofertaproducto.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    textAlign = TextAlign.Right,
                    text = producto.ofertaproducto,
                    fontSize = 14.sp
                )
            }
            if (producto.ofertaespe.isNotBlank()){
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = producto.ofertaespe,
                    fontSize = TamañoLetra.tamañoFuente.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = producto.precio,
                fontWeight = FontWeight.Bold,
                fontSize = TamañoLetra.tamañoFuente.sp
            )
            MarcarFavo(producto) { fav ->
                FavoritesRepository.toggleFavorite(context, fav)
            }
        }
    }
}