package com.example.kt_mobile_front

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kt_mobile_front.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: Int
) {
    object Catalog: NavigationItem(
        screen = Screen.Catalog,
        titleResId = R.string.navigation_item_catalog,
        icon = R.drawable.ic_home
    )
    object Chat: NavigationItem(
        screen = Screen.Chat,
        titleResId = R.string.navigation_item_chat,
        icon = R.drawable.ic_chat
    )
    object AddLot: NavigationItem(
        screen = Screen.AddLol,
        titleResId = R.string.navigation_item_addlot,
        icon = R.drawable.ic_add
    )
    object Profile: NavigationItem(
        screen = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        icon = R.drawable.ic_person
    )
}