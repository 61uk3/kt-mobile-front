package com.example.kt_mobile_front.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kt_mobile_front.NavigationItem
import com.example.kt_mobile_front.data.ItemChat
import com.example.kt_mobile_front.navigation.AppNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController = rememberNavController()){

    val chats = mutableListOf<ItemChat>().apply {
        repeat(20){
            add(
                ItemChat(id = it)
            )
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {

                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
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
                        onClick = { navHostController.navigate(item.screen.route) },
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
        AppNavGraph(
            navHostController = navHostController,
            catalogScreenContent = { CatalogScreen() },
            chatScreenContent = { ChatsScreen(chats = chats) },
            addLotScreenContent = { AddLotScreen() },
            profileScreenContent = { ProfileScreen() }

        )
    }
}