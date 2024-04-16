package com.example.kt_mobile_front.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kt_mobile_front.data.ItemChat
import com.example.kt_mobile_front.ui.theme.KtmobilefrontTheme

@Composable
fun ChatsScreen(
    chats: List<ItemChat>
){
    LazyColumn(contentPadding = PaddingValues(top= 12.dp, start = 4.dp, end = 4.dp, bottom = 92.dp)){
        items(
            items = chats,
            key = { it.id }
        ){chat ->
            ChatItem(chat = chat)
            if(chats.indexOf(chat) != chats.size-1){
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
private fun ChatItem(
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
                UserInfo(
                    modifier = Modifier,
                    userName = chat.userName,
                    chatId = chat.id,
                    userAvatar = chat.userAvatarId
                )
                Text(
                    text = chat.messageTime,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = chat.lotName,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = chat.messageText,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }

}

@Composable
private fun UserInfo(
    modifier: Modifier,
    userName: String,
    chatId: Int,
    userAvatar: Int
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${userName} ChatId: ${chatId}",
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = userAvatar),
            contentDescription = null
        )

    }
}