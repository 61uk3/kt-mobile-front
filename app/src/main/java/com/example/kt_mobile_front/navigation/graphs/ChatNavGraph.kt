package com.example.kt_mobile_front.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.kt_mobile_front.navigation.Graph
import com.example.kt_mobile_front.navigation.LotRouteScreen
import com.example.kt_mobile_front.navigation.MainRouteScreen
import com.example.kt_mobile_front.screens.ChatScreen

fun NavGraphBuilder.chatNavGraph(rootNavController: NavHostController) {
    navigation(
        route = Graph.ChatGraph,
        startDestination = MainRouteScreen.Chat.route
    ) {
        composable(
            route = "${Graph.ChatGraph}/{${Graph.ChatId}}",
            arguments = listOf(navArgument(Graph.ChatId){type = NavType.StringType})
        ) {
            val arguments = requireNotNull(it.arguments)
            ChatScreen(
                chatId = arguments.getString(Graph.ChatId, ""),
                onBackClickListener = {
                    rootNavController.navigateUp()
                },
                onUserClickListener = {
                    rootNavController.navigate(route = "${MainRouteScreen.ElseProfile.route}/${it}")
                },
                onLotClickListener = {
                    rootNavController.navigate(route = "${Graph.LotGraph}/${it}")
                }
            )
        }
    }
}