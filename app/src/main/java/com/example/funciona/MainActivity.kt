package com.example.funciona

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.funciona.ui.theme.ProductsViewModel
import com.example.funciona.ui.theme.TSGAppTheme
import com.example.funciona.ui.theme.TamañoLetra
import com.example.funciona.ui.theme.ThemeState
import com.example.tsgapp.AjustesPersonalizados
import com.example.tsgapp.ECuenta
import com.example.tsgapp.Favoritos
import com.example.tsgapp.principal


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeState.loadTheme(this)
        TamañoLetra.loadFont(this)
        setContent {
            TSGAppTheme {
                Surface {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = { BarraDespla(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "welcome",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("welcome") { principal() }
            composable("principal") { principal() }
            composable("favoritos") { Favoritos() }
            composable("ajustes") { Ajustes(navController) }
            composable("personalizacion") { AjustesPersonalizados() }
            composable("eliminar_cuenta") { ECuenta() }
            composable("cuenta") { VentanaCuenta() }
        }
    }
}

@Composable
fun Ajustes(navController: NavController) {
    val viewModel: ProductsViewModel = viewModel()
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Ajustes", fontSize = 30.sp, modifier = Modifier.padding(vertical = 20.dp))

            SettingItem("Cuenta") { navController.navigate("cuenta") }
            SettingItem("Personalización") { navController.navigate("personalizacion") }
            SettingItem("Eliminar cuenta") { navController.navigate("eliminar_cuenta") }

            Spacer(modifier = Modifier.padding(20.dp))

            Tiendas(
                onSelectionChange = { selected ->
                    viewModel.updateSelectedSupermarkets(selected) // Actualiza el ViewModel
                }
            )
        }
    }
}


@Composable
fun Tiendas( onSelectionChange: (List<String>) -> Unit) {
    val viewModel: ProductsViewModel = viewModel() // Obtiene el ViewModel compartido
    val selectedSupermarkets by viewModel.selectedSupermarkets.collectAsState()
    val selectedOptions = remember { mutableStateListOf(true, true, true) }

    val options = listOf("DIA", "Ahorramas", "Carrefour")

    LaunchedEffect(Unit) {
        options.forEachIndexed { index, label ->
            selectedOptions[index] = selectedSupermarkets.contains(label)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )
        Text(
            text = "Tiendas",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color.Gray
        )
        Divider(
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )
    }

    MultiChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                checked = selectedOptions[index],
                onCheckedChange = {
                    selectedOptions[index] = it
                    val selected = options.filterIndexed { idx, _ ->
                        selectedOptions[idx]
                    }
                    onSelectionChange(selected)
                },
                icon = { SegmentedButtonDefaults.Icon(selectedOptions[index]) },
                label = {
                    Text(label)
                }
            )
        }
    }
}

@Composable
fun SettingItem(text: String, onClick: () -> Unit) {
    val next = painterResource(id = R.drawable.next)
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text, fontSize = TamañoLetra.tamañoFuente.sp)
            Image(
                painter = next,
                contentDescription = "Next",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun BarraDespla(navController: NavController? = null){

    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        if (!ThemeState.isDarkMode){
            Button(
                onClick = { navController?.navigate("favoritos")},
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.favorite),
                    contentDescription = null
                )
            }
        } else {
            Button(
                onClick = { navController?.navigate("favoritos")},
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.favoritedark),
                    contentDescription = null
                )
            }}


        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 5.dp),
            color = Color.Gray
        )
        if (!ThemeState.isDarkMode) {
            Button(
                onClick = { navController?.navigate("principal")},
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = null
                )
            }} else {
            Button(
                onClick = { navController?.navigate("principal")},
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.homedark),
                    contentDescription = null
                )
            }
        }


        Divider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 5.dp),
            color = Color.Gray
        )

        if (!ThemeState.isDarkMode) {
            Button(
                onClick = { navController?.navigate("ajustes")},
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = null
                )
            }
        } else {
            Button(
                onClick = { navController?.navigate("ajustes")},
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.settingsdark),
                    contentDescription = null
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TSGAppTheme {}
}