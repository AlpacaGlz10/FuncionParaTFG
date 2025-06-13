package com.example.funciona.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.funciona.Ajustes
import com.example.funciona.home.InicioSes
import com.example.funciona.VentanaCuenta
import com.example.funciona.login.LoginScreen
import com.example.funciona.Signup.SignUpScreen
import com.example.tsgapp.AjustesPersonalizados
import com.example.tsgapp.ECuenta
import com.example.tsgapp.Favoritos
import com.example.tsgapp.principal
import com.google.firebase.auth.FirebaseAuth


@Composable
fun NavegationWrapper( auth: FirebaseAuth){
        val navController = rememberNavController()
        var isUserLoggedIn by remember { mutableStateOf(auth.currentUser != null) }

        // Observa cambios de sesión
        LaunchedEffect(Unit) {
            auth.addAuthStateListener {
                isUserLoggedIn = it.currentUser != null
            }
        }

        NavHost(
            navController = navController,
            startDestination = if (isUserLoggedIn) "principal" else "welcome"
        ) {
            composable("welcome") {
                InicioSes(
                    navigateToLogin = { navController.navigate("login") },
                    navigateToSignUp = { navController.navigate("signup") }
                )
            }

            composable("login") {
                LoginScreen(auth) {
                    navController.navigate("principal") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            }

            composable("signup") {
                SignUpScreen(auth)
            }

            composable("principal") {
                principal()
            }

            composable("favoritos") {
                Favoritos()
            }

            composable("ajustes") {
                Ajustes(navController)
            }

            composable("personalizacion") {
                AjustesPersonalizados()
            }

            composable("eliminar_cuenta") {
                ECuenta()
            }

            composable("cuenta") {
                VentanaCuenta(auth)
            }
        }
    }