package com.example.kt_mobile_front

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.kt_mobile_front.navigation.graphs.RootNavGraph
import com.example.kt_mobile_front.screens.AddLotScreen
import com.example.kt_mobile_front.screens.ChangePasswordScreen
import com.example.kt_mobile_front.screens.ChatScreen
import com.example.kt_mobile_front.screens.ChatsScreen
import com.example.kt_mobile_front.screens.EditLotScreen
import com.example.kt_mobile_front.screens.EditProfileScreen
import com.example.kt_mobile_front.screens.ElseProfileScreen
import com.example.kt_mobile_front.screens.LotScreen
import com.example.kt_mobile_front.screens.MainScreen
import com.example.kt_mobile_front.screens.SigninScreen
import com.example.kt_mobile_front.screens.SignupScreen
import com.example.kt_mobile_front.ui.theme.KtmobilefrontTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtmobilefrontTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //RootNavigationGraph(navHostController = rememberNavController())
                    //LotScreen()
                    //AddLotScreen()
                    //ElseProfileScreen()
                    //LotScreen(true)
                    //ChangePasswordScreen()
                    //ChatScreen()

                    //EditLotScreen()
                    RootNavGraph()

                }
            }
        }
    }
}

