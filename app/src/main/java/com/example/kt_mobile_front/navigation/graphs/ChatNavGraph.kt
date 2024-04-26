package com.example.kt_mobile_front.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.kt_mobile_front.navigation.Graph
import com.example.kt_mobile_front.navigation.LotRouteScreen
import com.example.kt_mobile_front.navigation.MainRouteScreen
import com.example.kt_mobile_front.screens.ChatScreen

fun NavGraphBuilder.chatNavGraph(rootNavController: NavHostController){
    navigation(
        route = Graph.ChatGraph,
        startDestination = MainRouteScreen.Chat.route
    ){
        composable(route = MainRouteScreen.Chat.route){
            ChatScreen(
                onBackClickListener = {
                    rootNavController.navigateUp()
                },
                onUserClickListener = {
                    rootNavController.navigate(route = MainRouteScreen.ElseProfile.route)
                },
                onLotClickListener = {
                    rootNavController.navigate(route = LotRouteScreen.Lot.route)
                }
            )
        }
    }
}