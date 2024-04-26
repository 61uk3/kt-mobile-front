package com.example.kt_mobile_front.navigation.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.kt_mobile_front.navigation.Graph
import com.example.kt_mobile_front.navigation.LotRouteScreen
import com.example.kt_mobile_front.navigation.MainRouteScreen
import com.example.kt_mobile_front.screens.EditLotScreen
import com.example.kt_mobile_front.screens.ElseProfileScreen
import com.example.kt_mobile_front.screens.LotScreen

fun NavGraphBuilder.lotNavGraph(rootNavController: NavHostController){
    navigation(
        route = Graph.LotGraph,
        startDestination = LotRouteScreen.Lot.route
    ){
        composable(route = LotRouteScreen.Lot.route){
            LotScreen(
                onBackClickListener = {
                    rootNavController.navigateUp()
                },
                onUserClickListener = {
                    rootNavController.navigate(route = MainRouteScreen.ElseProfile.route)
                },
                previousScreen = rootNavController.previousBackStackEntry?.destination?.route.toString(),
                onWriteClickListener = {
                    rootNavController.navigate(route = MainRouteScreen.Chat.route)
                }
            )
        }
        composable(route = MainRouteScreen.ElseProfile.route){
            ElseProfileScreen(
                onLotClickListener = {
                    rootNavController.navigate(LotRouteScreen.Lot.route)
                },
                onBackClickListener = {
                    rootNavController.navigateUp()
                }
            )
        }
    }
}

fun NavGraphBuilder.myLotNavGraph(rootNavController: NavHostController){
    navigation(
        route = Graph.MyLotGraph,
        startDestination = LotRouteScreen.MyLot.route
    ){
        composable(route = LotRouteScreen.MyLot.route){
            LotScreen(
                myLot = true,
                onBackClickListener = {
                    rootNavController.navigateUp()
                },
                onEditClickListener = {
                    rootNavController.navigate(route = LotRouteScreen.EditLot.route)
                }
            )
        }
        composable(route = LotRouteScreen.EditLot.route){
            EditLotScreen(
                onBackClickListener = {
                    rootNavController.navigateUp()
                }
            )
        }
    }
}