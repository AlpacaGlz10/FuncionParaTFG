package com.example.tsgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.funciona.R
import com.example.funciona.ui.theme.TamañoLetra
import com.example.funciona.ui.theme.ThemeState

class Personalizacion : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AjustesPersonalizados()
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjustesPersonalizados() {
    val contexto = LocalContext.current
    var mostrarMenu by remember { mutableStateOf(false) }
    var idiomaSeleccionado by remember { mutableStateOf("Español") }
    val primaryColor = MaterialTheme.colorScheme.primary


    Scaffold(
    topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            title = {
                Text("Personalización:")
            },
        )
    },
) {padding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 15.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp)
        ) {
            Text("Tema de la aplicación:", fontSize = TamañoLetra.tamañoFuente.sp)
            Switch(
                checked = ThemeState.isDarkMode,
                onCheckedChange = { ThemeState.saveTheme(contexto) },
                thumbContent = if (ThemeState.isDarkMode) {
                    {
                        Image(
                            painter = painterResource(id = R.drawable.moon),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else {
                    {
                        Image(
                            painter = painterResource(id = R.drawable.sun),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            )
        }

        Text("Tamaño de la letra:", fontSize = TamañoLetra.tamañoFuente.sp)
        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "Valor: ${TamañoLetra.tamañoFuente}", fontSize = TamañoLetra.tamañoFuente.sp)
        Spacer(modifier = Modifier.padding(horizontal = 5.dp))

        Slider(
            value = TamañoLetra.tamañoFuente.toFloat(),
            onValueChange = { newValue ->
                TamañoLetra.tamañoFuente = newValue.toInt()
                TamañoLetra.saveFontSize(contexto)
            },
            valueRange = 20f..30f,
            steps = 4,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.Blue,
                inactiveTrackColor = Color.Gray
            ),
            track = { sliderPositions ->
                Canvas(modifier = Modifier.fillMaxWidth()) {
                    val trackHeight = 4.dp.toPx()
                    val cornerRadius = 2.dp.toPx()

                    drawRoundRect(
                        color = Color.Gray,
                        size = Size(size.width, trackHeight),
                        cornerRadius = CornerRadius(cornerRadius)
                    )

                    val normalizedValue = (TamañoLetra.tamañoFuente - 20) / (30 - 20f)
                    val filledWidth = normalizedValue * size.width

                    drawRoundRect(
                        color = primaryColor,
                        size = Size(filledWidth, trackHeight),
                        cornerRadius = CornerRadius(cornerRadius)
                    )
                }
            },
            thumb = {
                val size = 20.dp
                Box(
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape)
                        .background(primaryColor)
                        .border(2.dp, primaryColor)
                )
            }
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Idioma:", fontSize = TamañoLetra.tamañoFuente.sp)

            Spacer(modifier = Modifier.width(16.dp))

            Box {
                Button(
                    onClick = { mostrarMenu = true },
                    modifier = Modifier
                        .width(150.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    Text(
                        text = idiomaSeleccionado,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start,
                        fontSize = TamañoLetra.tamañoFuente.sp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.down),
                        contentDescription = "Seleccionar idioma",
                        modifier = Modifier.size(20.dp)
                    )
                }

                DropdownMenu(
                    expanded = mostrarMenu,
                    onDismissRequest = { mostrarMenu = false },
                    modifier = Modifier.width(150.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("Español", fontSize = TamañoLetra.tamañoFuente.sp) },
                        onClick = {
                            idiomaSeleccionado = "Español"
                            mostrarMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Inglés", fontSize = TamañoLetra.tamañoFuente.sp) },
                        onClick = {
                            idiomaSeleccionado = "Inglés"
                            mostrarMenu = false
                            }
                        )
                    }
                }
            }
        }
    }
}
