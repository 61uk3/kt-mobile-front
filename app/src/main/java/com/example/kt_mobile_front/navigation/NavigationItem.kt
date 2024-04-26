package com.example.kt_mobile_front.navigation

import com.example.kt_mobile_front.R

sealed class NavigationItem(
    val screen: MainRouteScreen,
    val titleResId: Int,
    val icon: Int
) {
    object Catalog: NavigationItem(
        screen = MainRouteScreen.Catalog,
        titleResId = R.string.navigation_item_catalog,
        icon = R.drawable.ic_home
    )
    object Chat: NavigationItem(
        screen = MainRouteScreen.Chats,
        titleResId = R.string.navigation_item_chat,
        icon = R.drawable.ic_chat
    )
    object AddLot: NavigationItem(
        screen = MainRouteScreen.Add,
        titleResId = R.string.navigation_item_addlot,
        icon = R.drawable.ic_add
    )
    object Profile: NavigationItem(
        screen = MainRouteScreen.Profile,
        titleResId = R.string.navigation_item_profile,
        icon = R.drawable.ic_person
    )
}