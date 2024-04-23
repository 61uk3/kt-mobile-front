package com.example.kt_mobile_front.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.LotCard
import com.example.kt_mobile_front.components.UserAvatarName
import com.example.kt_mobile_front.data.ItemShortLot

@Composable
fun ElseProfileScreen() {
    val lotList = mutableListOf<ItemShortLot>().apply {
        add(ItemShortLot(id = 1, photo = "фото", name = "шруповерт"))
        add(ItemShortLot(id = 2, photo = "фото", name = "перфоратор"))
        add(ItemShortLot(id = 3, photo = "фото", name = "набор отверток"))
    }
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        columns = GridCells.Fixed(2)
    ) {
        item(
            span = { GridItemSpan(2) }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }
            }

        }
        item(
            span = { GridItemSpan(2) }
        ) {
            User(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                userName = "Александр",
                registrationDate = "12.12.2023",
                countLot = 10
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        for (i in lotList) {
            item {
                LotCard(
                    modifier = Modifier
                        .size(220.dp)
                        .padding(4.dp)
                        .clickable {  },
                    itemShortLot = i
                )
            }
        }
    }
}



@Composable
private fun User(
    modifier: Modifier = Modifier,
    userName: String,
    registrationDate: String,
    countLot: Int
) {
    Row(
        modifier = modifier
    ) {
        UserAvatarName(
            modifier = Modifier
                .size(112.dp)
                .clip(CircleShape),
            userAvatar = R.drawable.user_avatar
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = userName, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Дата регистрации: ${registrationDate}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Количество публикаций: ${countLot}")
        }
    }

}