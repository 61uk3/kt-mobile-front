package com.example.kt_mobile_front.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kt_mobile_front.data.ShortChatData
import com.example.kt_mobile_front.requests.getChats
import kotlinx.coroutines.launch

@Composable
fun ChatsScreen(
    onChatClickListener: (String) -> Unit
) {
    val (chatList, setChatList) = remember {
        mutableStateOf<List<ShortChatData>>(listOf())
    }
    val coroutineScope = rememberCoroutineScope()
    SideEffect {
        coroutineScope.launch {
            try {
                setChatList(getChats())
            } catch (t: Exception) {
                setChatList(listOf())
            }
        }
    }
    LazyColumn(
        contentPadding = PaddingValues(
            top = 12.dp,
            start = 4.dp,
            end = 4.dp,
            bottom = 92.dp
        )
    ) {
        items(
            items = chatList,
            key = { it.id }
        ) { chat ->
            ChatItem(
                chat = chat,
                onChatClickListener = onChatClickListener
            )
            if (chatList.indexOf(chat) != chatList.size - 1) {
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
    chat: ShortChatData,
    onChatClickListener: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onChatClickListener(chat.id) }
    ) {
        AsyncImage(
            modifier = Modifier
                .size(64.dp),
            model = chat.photo_lots,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = chat.name_lots,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = chat.date,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = chat.sender_name + ": ")
                Text(
                    text = chat.last_message,
                    fontSize = 16.sp
                )
            }
        }
    }
}



