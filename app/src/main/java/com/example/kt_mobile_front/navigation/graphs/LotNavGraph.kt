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
        composable(
            route = "${Graph.LotGraph}/{${Graph.LotId}}",
            arguments = listOf(navArgument(Graph.LotId){type = NavType.StringType})
        ){
            val arguments = requireNotNull(it.arguments)
            LotScreen(
                lotId = arguments.getString(Graph.LotId, ""),
                onBackClickListener = {
                    rootNavController.navigateUp()
                },
                onUserClickListener = {
                    rootNavController.navigate(route = "${MainRouteScreen.ElseProfile.route}/${it}")
                },
                previousScreen = rootNavController.previousBackStackEntry?.destination?.route.toString(),
                onWriteClickListener = {
                    rootNavController.navigate(route = "${Graph.ChatGraph}/${it}")
                }
            )
        }
        composable(
            route = "${MainRouteScreen.ElseProfile.route}/{${Graph.UserId}}",
            arguments = listOf(navArgument(Graph.UserId){type = NavType.StringType})
        ){
            val arguments = requireNotNull(it.arguments)
            ElseProfileScreen(
                userId = arguments.getString(Graph.UserId, ""),
                onLotClickListener = {
                    rootNavController.navigate("${Graph.LotGraph}/${it}")
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
        composable(
            route = "${Graph.MyLotGraph}/{${Graph.LotId}}",
            arguments = listOf(navArgument(Graph.LotId){type = NavType.StringType})
        ){
            val arguments = requireNotNull(it.arguments)
            LotScreen(
                lotId = arguments.getString(Graph.LotId, ""),
                myLot = true,
                onBackClickListener = {
                    rootNavController.navigateUp()
                },
                onEditClickListener = {
                    rootNavController.navigate(route = "${LotRouteScreen.EditLot.route}/${it}")
                },
                onDelClickListener = {
                    rootNavController.navigateUp()
                }
            )
        }
        composable(
            route = "${LotRouteScreen.EditLot.route}/{${Graph.EditLotId}}",
            arguments = listOf(navArgument(Graph.EditLotId){type = NavType.StringType})
        ){
            val arguments = requireNotNull(it.arguments)
            EditLotScreen(
                lotId = arguments.getString(Graph.EditLotId, ""),
                onBackClickListener = {
                    rootNavController.navigateUp()
                }
            )
        }
    }
}