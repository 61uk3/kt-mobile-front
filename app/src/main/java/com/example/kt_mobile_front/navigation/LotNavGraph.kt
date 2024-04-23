package com.example.kt_mobile_front.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.kt_mobile_front.screens.LotScreen

fun NavGraphBuilder.lotNavGraph(navHostController: NavHostController){
    navigation(
        route = Graph.LOT,
        startDestination = Screen.Lot.route
    ){
        composable(route = Screen.Lot.route){
            LotScreen()
        }
    }
}