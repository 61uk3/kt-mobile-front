package com.example.kt_mobile_front.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kt_mobile_front.R

@Composable
fun UserAvatarName(
    modifier: Modifier = Modifier,
    userAvatar: String,
    userName: String? = ""
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = modifier,
            model = userAvatar,
            contentDescription = null
        )
        if (userName != "") {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = userName ?: "",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}