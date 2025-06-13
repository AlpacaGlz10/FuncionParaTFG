package com.example.funciona.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.funciona.R
import com.example.funciona.ui.theme.IntenseRed
import com.example.funciona.ui.theme.Printgris
import com.example.funciona.ui.theme.backgrowi2
import com.example.funciona.ui.theme.grenprint
import com.example.funciona.ui.theme.pitnrua2
import com.example.funciona.ui.theme.purpvr2
import com.example.funciona.ui.theme.sombris
import com.example.funciona.ui.theme.yellowprin2

@Preview
@Composable
fun InicioSes( navigateToLogin:() -> Unit ={}, navigateToSignUp: () -> Unit = {}){

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(sombris),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.weight(2f))

        Image(
            painter = painterResource(id = R.drawable.smartprice),
            contentDescription = "Logo",
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .weight(2f)
        )
        Spacer(Modifier.padding(10.dp))
        Text(
            "Smart Price",
            color = pitnrua2,
            fontSize = 35.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.weight(4f))

        Text(
            "Welcome To App",
            color = backgrowi2,
            fontSize = 35.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.weight(0.5f))

        Button(
            onClick = { navigateToLogin()},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 32.dp)
                .border(2.dp, yellowprin2, CircleShape),
            colors = ButtonDefaults.buttonColors(containerColor = purpvr2)

        ) {
            Text(
                "Iniciar secion",
                color = backgrowi2,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(8.dp))
        CustomButton(
            Modifier.clickable{ navigateToLogin},
            painterResource(id = R.drawable.googlelogo),
            "Google"
        )
        Text(
            text = "Registrase",
            color = Color.White,
            modifier = Modifier.padding(10.dp)
                .clickable{navigateToSignUp()},
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CustomButton(modifier: Modifier, painter: Painter, title: String){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 32.dp)
            .border(2.dp, yellowprin2, CircleShape)
            .background(IntenseRed, shape = CircleShape),
        contentAlignment = Alignment.CenterStart)
    {
        Image(
            painter =painter,
            contentDescription = "google boton",
            modifier = Modifier.padding(start = 16.dp).size(16.dp))
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold)
    }
}