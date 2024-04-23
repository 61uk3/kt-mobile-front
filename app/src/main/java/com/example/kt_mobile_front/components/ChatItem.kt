package com.example.kt_mobile_front.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kt_mobile_front.data.ItemChat

@Composable
fun ChatItem(
    chat: ItemChat
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { }
    ){
        Image(
            modifier = Modifier
                .size(64.dp),
            painter = painterResource(id = chat.lotImageId),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column{
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = chat.lotName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = chat.messageTime,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = chat.userName + ": ")
                Text(
                    text = chat.messageText,
                    fontSize = 16.sp
                )
            }
        }
    }
}