package com.example.kt_mobile_front.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.UserAvatarName

@Composable
fun ProfileScreen(){
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 84.dp),
        horizontalArrangement = Arrangement.Center,
        columns = GridCells.Fixed(2)) {
        item(
            span = { GridItemSpan(2) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_menu_vert), contentDescription = null)
                }
            }
        }
        item(
            span = { GridItemSpan(2) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                UserAvatarName(
                    modifier = Modifier
                        .size(112.dp)
                        .clip(CircleShape),
                    userAvatar = R.drawable.user_avatar,
                    userName = "Александр"
                )
            }

        }
        items(20){
            Card(
                modifier = Modifier
                    .size(220.dp)
                    .padding(4.dp)
            ) {

            }
        }
    }
}