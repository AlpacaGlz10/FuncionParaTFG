package com.example.funciona

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.funciona.ui.theme.TamañoLetra
import com.google.firebase.auth.FirebaseAuth

class Cuenta : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        cosas nuevas que he añadido
         */
        val auth = FirebaseAuth.getInstance()
        setContent {
            VentanaCuenta(auth)
        }
    }
}

@Composable
fun VentanaCuenta(auth: FirebaseAuth) {
    var text1 by rememberSaveable { mutableStateOf("") }
    var text2 by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        TextField(
            value = text1,
            onValueChange = { text1 = it },
            label = { Text("User") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = text2,
            onValueChange = { text2 = it },
            label = { Text("Password") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(15.dp))
        RecuperarContrasena()

        Spacer(modifier = Modifier.height(30.dp))
        DividerWithText(text = "or")

        Spacer(modifier = Modifier.height(30.dp))
        LoginButton(text = "Gmail", iconRes = R.drawable.gmail)
        LoginButton(text = "Google", iconRes = R.drawable.smartprice)
    }
}

@Composable
fun RecuperarContrasena() {
    Button(
        onClick = {/*TODO()*/},
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Text(
            text = "Olvidaste tu contraseña?",
            fontSize = TamañoLetra.tamañoFuente.sp,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
    }
}

@Composable
fun DividerWithText(text: String) {
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
            text = text,
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
}

@Composable
fun LoginButton(text: String, iconRes: Int) {
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = { /* Acción al hacer clic */ },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = TamañoLetra.tamañoFuente.sp
            )
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
            )
        }
    }
}