package com.example.kt_mobile_front.navigation.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.kt_mobile_front.navigation.AuthRouteScreen
import com.example.kt_mobile_front.navigation.Graph
import com.example.kt_mobile_front.screens.SigninScreen
import com.example.kt_mobile_front.screens.SignupScreen

fun NavGraphBuilder.authNavGraph(rootNavController: NavHostController){
    navigation(
        route = Graph.AuthGraph,
        startDestination = AuthRouteScreen.SignIn.route
    ){
        composable(route = AuthRouteScreen.SignIn.route){
            SigninScreen(
                signinClickListener = {
                    rootNavController.navigate(route = Graph.MainScreenGraph)
                },
                signupClickListener = {
                    rootNavController.navigate(route = AuthRouteScreen.SignUp.route)
                }
            )
        }
        composable(route = AuthRouteScreen.SignUp.route){
            SignupScreen(
                loginClickListener = {
                    rootNavController.navigateUp()
                }
            )
        }
    }
}