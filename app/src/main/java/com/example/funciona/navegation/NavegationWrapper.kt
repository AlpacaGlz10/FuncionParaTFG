package com.example.funciona.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.funciona.home.InicioSes
import com.example.funciona.VentanaCuenta
import com.example.funciona.login.LoginScreen
import com.example.funciona.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun NavegationWrapper(navHostController: NavHostController, auth: FirebaseAuth){

    NavHost(navController = navHostController, startDestination = "inicio") {
        composable("inicio") {
            InicioSes(
                navigateToLogin = {navHostController.navigate("logIn")},
                navigateToSignUp = {navHostController.navigate("signUp")}
            )
        }
        composable("logIn") {
            LoginScreen(auth){ navHostController.navigate("Home")}
        }
        composable("signUp"){
            SignUpScreen(auth)
        }
        composable("VentanaCuenta") {
            VentanaCuenta(auth)
        }

    }
}