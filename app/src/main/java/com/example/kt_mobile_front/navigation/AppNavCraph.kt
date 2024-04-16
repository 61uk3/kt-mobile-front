package com.example.kt_mobile_front.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kt_mobile_front.screens.CatalogScreen

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    catalogScreenContent: @Composable () -> Unit,
    chatScreenContent: @Composable () -> Unit,
    addLotScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
){
    NavHost(
        navController = navHostController,
        route = Graph.CATALOG,
        startDestination = Screen.Catalog.route
    ){
        composable(Screen.Catalog.route){
            catalogScreenContent()
        }
        composable(Screen.Chat.route){
            chatScreenContent()
        }
        composable(Screen.AddLol.route){
            addLotScreenContent()
        }
        composable(Screen.Profile.route){
            profileScreenContent()
        }
    }
}
