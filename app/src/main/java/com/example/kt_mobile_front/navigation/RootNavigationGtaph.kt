package com.example.kt_mobile_front.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kt_mobile_front.screens.MainScreen

@Composable
fun RootNavigationGraph(navHostController: NavHostController){
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        AuthNavigationGraph(navHostController = navHostController)
        composable(route = Graph.CATALOG){
            MainScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val CATALOG = "catalog_graph"
}