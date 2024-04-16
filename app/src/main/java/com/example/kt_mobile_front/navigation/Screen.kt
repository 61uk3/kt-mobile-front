package com.example.kt_mobile_front.navigation

sealed class Screen(
    val route: String
){
    object Catalog: Screen(ROUTE_CATALOG)
    object Chat: Screen(ROUTE_CHAT)
    object AddLol: Screen(ROUTE_ADD_LOT)
    object Profile: Screen(ROUTE_PROFILE)
    object Signup: Screen(ROUTE_SIGNUP)
    object Signin: Screen(ROUTE_SIGNIN)

    private companion object{
        const val ROUTE_CATALOG = "catalog"
        const val ROUTE_CHAT = "chat"
        const val ROUTE_ADD_LOT = "add_lot"
        const val ROUTE_PROFILE = "profile"
        const val ROUTE_SIGNUP = "signup"
        const val ROUTE_SIGNIN = "signin"
    }
}
