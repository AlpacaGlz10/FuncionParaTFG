package com.example.tsgapp

import androidx.compose.runtime.Composable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.funciona.ui.theme.TamañoLetra
import com.example.funciona.ui.theme.ThemeState
import android.os.Bundle
import androidx.compose.ui.tooling.preview.Preview

class EliminarCuenta : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ECuenta()
        }
    }
}
@Preview
@Composable
fun ECuenta(){
    val scrollState = rememberScrollState()
    var expandido by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.padding(vertical = TamañoLetra.tamañoFuente.dp),
            onClick = {/**/}
        ) {
            Text("Eliminar cuenta")
        }

        if (!expandido) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .clickable{ expandido = !expandido }
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        "Aviso Legal – Eliminación de Cuenta de Usuario\n" +
                                "Al utilizar nuestros servicios, usted acepta..."
                        , fontSize = TamañoLetra.tamañoFuente.sp)
                }
            }
        } else {
            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .clickable{ expandido = !expandido }
                
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 700.dp)
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .verticalScroll(scrollState)
                ) {
                    Texto()
                }
            }
        }
    }
}

@Composable
fun Texto() {
    Box{
        Column {
            Text(
                "Aviso Legal – Eliminación de Cuenta de Usuario\n" +
                        "Al utilizar nuestros servicios, usted acepta y comprende que la eliminación de su cuenta es un proceso irreversible que implica la pérdida permanente de todos los datos asociados a dicha cuenta. Este aviso tiene como finalidad informarle claramente sobre las consecuencias del cierre definitivo de su perfil.\n"
                , fontSize = TamañoLetra.tamañoFuente.sp)
            RayaDivisora()
            Text(
                "\n1. Proceso de eliminación de cuenta\n" +
                        "El usuario podrá solicitar la eliminación de su cuenta en cualquier momento desde la sección de configuración correspondiente o poniéndose en contacto con nuestro soporte. Una vez confirmada la solicitud, se iniciará el proceso de eliminación que puede durar hasta 30 días hábiles, plazo en el cual se verificarán posibles obligaciones legales o técnicas pendientes.\n"
                , fontSize = TamañoLetra.tamañoFuente.sp
            )
            RayaDivisora()
            Text(
                "\n2. Pérdida de datos\n" +
                        "La eliminación de la cuenta implica la eliminación definitiva de toda la información asociada , incluyendo pero no limitado a:\n" +
                        "\n" +
                        "Datos personales proporcionados durante el registro y uso del servicio.\n" +
                        "Contenido generado por el usuario (publicaciones, comentarios, imágenes, archivos, etc.).\n" +
                        "Historial de actividad, preferencias y configuraciones.\n" +
                        "Relaciones o interacciones con otros usuarios.\n" +
                        "Una vez completado el proceso, no será posible recuperar ningún dato ni acceder nuevamente a la cuenta .\n"
                , fontSize = TamañoLetra.tamañoFuente.sp
            )
            RayaDivisora()
            Text(
                "\n3. Consentimiento informado\n" +
                        "Mediante la confirmación de la eliminación de su cuenta, usted declara haber leído, entendido y aceptado que:\n" +
                        "\n" +
                        "Comprende las implicaciones del borrado definitivo de sus datos.\n" +
                        "Está tomando una decisión consciente e informada.\n" +
                        "No se mantendrá respaldo alguno de su información tras la eliminación, salvo que la ley lo exija.\n"
                , fontSize = TamañoLetra.tamañoFuente.sp
            )
            RayaDivisora()
            Text(
                "\n4. Conservación parcial de datos\n" +
                        "De acuerdo con las normativas vigentes, ciertos datos podrán ser conservados únicamente con fines legales, contables o de seguridad, sin estar vinculados a su identidad personal. Dichos datos no serán utilizados con fines comerciales ni de procesamiento posterior.\n"
                , fontSize = TamañoLetra.tamañoFuente.sp
            )
            RayaDivisora()
            Text(
                "\n5. Responsabilidad del usuario\n" +
                        "Es responsabilidad del usuario asegurarse de descargar o guardar cualquier información relevante antes de proceder con la eliminación de su cuenta. La empresa no asume responsabilidad alguna por la pérdida de datos derivada del cierre de la cuenta.\n"
                , fontSize = TamañoLetra.tamañoFuente.sp
            )
            RayaDivisora()
            Text(
                "\nEste aviso forma parte de los Términos y Condiciones del servicio y está sujeto a las leyes aplicables en materia de protección de datos y privacidad. Si tienes dudas sobre este proceso, puedes contactarnos a través de [correo de contacto] o visitar nuestra sección de privacidad."
                , fontSize = TamañoLetra.tamañoFuente.sp
            )
        }
    }
}

@Composable
fun RayaDivisora(){
    if (!ThemeState.isDarkMode) {
        Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.Black)
    } else {
        Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.White)
    }
}