package com.example.kt_mobile_front.navigation

object Graph {
    const val RootGraph = "rootGraph"
    const val AuthGraph = "authGraph"
    const val MainScreenGraph = "mainScreenGraph"
    const val LotGraph = "lotGraph"
    const val MyLotGraph = "myLotGraph"
    const val ChatGraph = "chatGraph"
    const val EditProfileGraph = "editProfileGraph"
    const val ChangePasswordGraph = "changePasswordGraph"
    const val LotId = "lotId"
    const val EditLotId = "editLotId"
    const val UserId = "userId"
    const val ChatId = "chatId"
}

sealed class AuthRouteScreen(val route: String){
    object SignIn : AuthRouteScreen("signIn")
    object SignUp : AuthRouteScreen("signUp")
}
sealed class MainRouteScreen(val route: String){
    object Catalog : MainRouteScreen("catalog")
    object Chats : MainRouteScreen("chats")
    object Add : MainRouteScreen("add")
    object Profile : MainRouteScreen("profile")
    object  ElseProfile : MainRouteScreen("elseprofile")
    object Chat : MainRouteScreen("chat")
}
sealed class LotRouteScreen(val route: String){
    object Lot : LotRouteScreen("lot")
    object MyLot : LotRouteScreen("mylot")
    object EditLot : LotRouteScreen("editlot")
}

sealed class ProfileRouteScreen(val route: String){
    object EditProfile : ProfileRouteScreen("editprofile")
    object ChangePassword : ProfileRouteScreen("changepassword")
}
