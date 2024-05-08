package com.example.kt_mobile_front.screens

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.LotCard
import com.example.kt_mobile_front.components.UserAvatarName
import com.example.kt_mobile_front.data.UserData
import com.example.kt_mobile_front.requests.getUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onLotClickListener: (String) -> Unit,
    onEditClickListener: () -> Unit,
    onPasswordClickListener: () -> Unit
) {
    val (User, setUser) = remember {
        mutableStateOf<UserData?>(null)
    }
    val coroutineScope = rememberCoroutineScope()
    SideEffect {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                setUser(getUser())
            } catch (t: Exception) {

            }
        }
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 84.dp),
        horizontalArrangement = Arrangement.Center,
        columns = GridCells.Fixed(2)
    ) {
        item(
            span = { GridItemSpan(2) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Box {
                    IconButton(
                        onClick = { expanded = true }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_menu_vert),
                            contentDescription = null
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        TextButton(
                            onClick = {
                                expanded = false
                                onEditClickListener()
                            },
                        ) {
                            Text(text = "Редактировать профиль")
                        }
                        TextButton(
                            onClick = {
                                expanded = false
                                onPasswordClickListener()
                            }
                        ) {
                            Text(text = "Сменить пароль")
                        }
                        TextButton(
                            onClick = {
                                expanded = false
                            }
                        ) {
                            Text(text = "Выйти")
                        }
                    }
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
                    userAvatar = User?.photo ?: "",
                    userName = User?.name ?: ""
                )
            }

        }
        items(User?.items ?: listOf()){
            LotCard(
                modifier = Modifier
                    .size(220.dp)
                    .padding(4.dp)
                    .clickable { onLotClickListener(it.id) },
                shortLotData = it
            )
        }
    }
}