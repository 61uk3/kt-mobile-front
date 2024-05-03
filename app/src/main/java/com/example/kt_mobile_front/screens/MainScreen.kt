package com.example.kt_mobile_front.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kt_mobile_front.navigation.NavigationItem
import com.example.kt_mobile_front.navigation.graphs.MainNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    rootNavHostController: NavHostController,
    homeNavHostController: NavHostController = rememberNavController()
){
    Scaffold(
        bottomBar = {
            NavigationBar {

                val navBackStackEntry by homeNavHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val items = listOf(
                    NavigationItem.Catalog,
                    NavigationItem.Chat,
                    NavigationItem.AddLot,
                    NavigationItem.Profile
                )
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.screen.route,
                        onClick = { homeNavHostController.navigate(item.screen.route) },
                        icon = {
                            Icon(painter = painterResource(id = item.icon), contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        }
                    )
                }
            }
        }
    ) {
        MainNavGraph(
            rootNavController = rootNavHostController,
            homeNavController = homeNavHostController
        )
        /*AppNavGraph(
            navHostController = navHostController,
            catalogScreenContent = { CatalogScreen() },
            chatScreenContent = { ChatsScreen(chats = chats) },
            addLotScreenContent = { AddLotScreen() },
            profileScreenContent = { ProfileScreen() }

        )*/
    }
}