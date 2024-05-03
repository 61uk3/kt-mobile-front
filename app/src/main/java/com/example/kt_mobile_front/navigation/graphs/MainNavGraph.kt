package com.example.kt_mobile_front.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.kt_mobile_front.navigation.Graph
import com.example.kt_mobile_front.navigation.LotRouteScreen
import com.example.kt_mobile_front.navigation.MainRouteScreen
import com.example.kt_mobile_front.navigation.ProfileRouteScreen
import com.example.kt_mobile_front.screens.AddLotScreen
import com.example.kt_mobile_front.screens.CatalogScreen
import com.example.kt_mobile_front.screens.ChangePasswordScreen
import com.example.kt_mobile_front.screens.ChatsScreen
import com.example.kt_mobile_front.screens.EditLotScreen
import com.example.kt_mobile_front.screens.EditProfileScreen
import com.example.kt_mobile_front.screens.LotScreen
import com.example.kt_mobile_front.screens.ProfileScreen

@Composable
fun MainNavGraph(
    rootNavController:NavHostController,
    homeNavController: NavHostController
){
    NavHost(
        navController = homeNavController,
        route = Graph.MainScreenGraph,
        startDestination = MainRouteScreen.Catalog.route
    ){
        composable(route = MainRouteScreen.Catalog.route){
            CatalogScreen(
                onLotClickListener = {
                    rootNavController.navigate(route = "${Graph.LotGraph}/${it}")
                }
            )
        }
        composable(route = MainRouteScreen.Chats.route){
            ChatsScreen(
                onChatClickListener = {
                    rootNavController.navigate(route = Graph.ChatGraph)
                }
            )
        }
        composable(route = MainRouteScreen.Add.route){
            AddLotScreen()
        }
        composable(route = MainRouteScreen.Profile.route){
            ProfileScreen(
                onLotClickListener = {
                    rootNavController.navigate(route = "${Graph.MyLotGraph}/${it}")
                },
                onEditClickListener = {
                    rootNavController.navigate(route = Graph.EditProfileGraph)
                },
                onPasswordClickListener = {
                    rootNavController.navigate(route = Graph.ChangePasswordGraph)
                }
            )
        }
    }
}

fun NavGraphBuilder.editProfileNavGraph(rootNavController: NavHostController){
    navigation(
        route = Graph.EditProfileGraph,
        startDestination = ProfileRouteScreen.EditProfile.route
    ){
        composable(route = ProfileRouteScreen.EditProfile.route){
            EditProfileScreen(
                onBackClickListener = {
                    rootNavController.navigateUp()
                }
            )
        }
    }
}
fun NavGraphBuilder.changePasswordNavGraph(rootNavController: NavHostController){
    navigation(
        route = Graph.ChangePasswordGraph,
        startDestination = ProfileRouteScreen.ChangePassword.route
    ){
        composable(route = ProfileRouteScreen.ChangePassword.route){
            ChangePasswordScreen(
                onBackClickListener = {
                    rootNavController.navigateUp()
                }
            )
        }
    }
}