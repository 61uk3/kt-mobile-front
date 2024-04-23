package com.example.kt_mobile_front.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kt_mobile_front.R

@Composable
fun ButtonToSite(){
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://rsbor.ru/")) }

    IconButton(modifier = Modifier.size(80.dp), onClick = { context.startActivity(intent) }) {
        Image(
            modifier = Modifier.size(60.dp),
            painter = painterResource(id = R.drawable.header_logo),
            contentDescription = null
        )
    }
}