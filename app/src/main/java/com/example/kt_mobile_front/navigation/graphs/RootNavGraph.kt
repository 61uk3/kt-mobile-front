package com.example.kt_mobile_front.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kt_mobile_front.navigation.Graph
import com.example.kt_mobile_front.screens.MainScreen

@Composable
fun RootNavGraph(){
    val rootNavController = rememberNavController()
    NavHost(
        navController = rootNavController,
        route = Graph.RootGraph,
        startDestination = Graph.AuthGraph
    ){
        authNavGraph(rootNavController = rootNavController)
        composable(route = Graph.MainScreenGraph){
            MainScreen(rootNavHostController = rootNavController)
        }
        lotNavGraph(rootNavController = rootNavController)
        myLotNavGraph(rootNavController = rootNavController)
        chatNavGraph(rootNavController = rootNavController)
        editProfileNavGraph(rootNavController = rootNavController)
        changePasswordNavGraph(rootNavController = rootNavController)
    }

}