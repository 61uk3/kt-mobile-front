package com.example.kt_mobile_front.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.data.ChatData
import com.example.kt_mobile_front.data.LotData
import com.example.kt_mobile_front.data.Message
import com.example.kt_mobile_front.requests.getChatById
import com.example.kt_mobile_front.requests.getItemById
import com.example.kt_mobile_front.requests.postMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    chatId: String,
    onBackClickListener: () -> Unit,
    onUserClickListener: (String) -> Unit,
    onLotClickListener: (String) -> Unit
) {
    val (message, setMessage) = remember {
        mutableStateOf("")
    }
    val (Chat, setChat) = remember {
        mutableStateOf<ChatData?>(null)
    }
    val coroutineScope = rememberCoroutineScope()
    SideEffect {
        coroutineScope.launch {
            while (true) {
                try {
                    setChat(getChatById(chatId))
                } catch (_: Exception) {

                }
                delay(7000)
            }
        }
    }
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.clickable {
                                onUserClickListener(Chat?.user_id ?: "")
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                modifier = Modifier.size(40.dp),
                                model = Chat?.user_photo ?: "",
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = Chat?.user_name ?: "",
                                fontSize = 24.sp
                            )
                        }

                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackClickListener() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = null
                            )
                        }
                    }
                )
                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 56.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                        .clickable {
                            onLotClickListener(Chat?.lot_id ?: "")
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = Chat?.lot_name  ?: "", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.size(50.dp)) {
                        AsyncImage(
                            model = Chat?.lot_photo  ?: "",
                            contentDescription = null
                        )
                    }
                }
                HorizontalDivider()
            }

        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp, max = 150.dp)
            ) {
                HorizontalDivider()
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = message,
                    onValueChange = setMessage,
                    placeholder = {
                        Text(text = "Сообщение")
                    },
                    trailingIcon = {
                        if (message != "") {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    postMessage(Chat!!.lot_id, message)
                                }
                                setMessage("")

                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_send),
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    )
                )
            }

        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            for (m in Chat?.messages ?: listOf()) {
                if (m.id_sender == (Chat?.user_id ?: "")) {
                    item {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Card(
                                modifier = Modifier
                                    .widthIn(max = 310.dp)
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                shape = RoundedCornerShape(
                                    bottomStart = 0.dp,
                                    topStart = 12.dp,
                                    topEnd = 12.dp,
                                    bottomEnd = 12.dp
                                )
                            ) {
                                Text(text = m.message, modifier = Modifier.padding(6.dp), fontSize = 20.sp)
                            }
                        }
                    }
                } else {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Card(
                                modifier = Modifier
                                    .widthIn(max = 310.dp)
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                shape = RoundedCornerShape(
                                    bottomStart = 12.dp,
                                    topStart = 12.dp,
                                    topEnd = 12.dp,
                                    bottomEnd = 0.dp
                                )
                            ) {
                                Text(text = m.message, modifier = Modifier.padding(6.dp), fontSize = 20.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}