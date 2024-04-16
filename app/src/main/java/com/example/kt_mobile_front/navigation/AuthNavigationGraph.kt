package com.example.kt_mobile_front.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.kt_mobile_front.screens.SigninScreen
import com.example.kt_mobile_front.screens.SignupScreen

fun NavGraphBuilder.AuthNavigationGraph(navHostController: NavHostController){
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = Screen.Signin.route
    ){
        composable(Screen.Signin.route){
            SigninScreen(
                signinClickListener = {
                    navHostController.popBackStack()
                    navHostController.navigate(Graph.CATALOG)
                },
                signupClickListener = {
                    navHostController.navigate(Screen.Signup.route)
                }
            )
        }
        composable(Screen.Signup.route){
            SignupScreen(
                loginClickListener = {
                    navHostController.navigate(Screen.Signin.route)
                }
            )
        }
    }
}

