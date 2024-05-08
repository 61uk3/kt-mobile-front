package com.example.kt_mobile_front.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.data.ChatData
import com.example.kt_mobile_front.data.LotData
import com.example.kt_mobile_front.navigation.Graph
import com.example.kt_mobile_front.navigation.LotRouteScreen
import com.example.kt_mobile_front.navigation.MainRouteScreen
import com.example.kt_mobile_front.requests.delItem
import com.example.kt_mobile_front.requests.getItemById
import com.example.kt_mobile_front.requests.postCreateChat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LotScreen(
    lotId: String = "",
    previousScreen: String = "",
    myLot: Boolean = false,
    onBackClickListener: () -> Unit,
    onUserClickListener: (String) -> Unit = {},
    onWriteClickListener: (String) -> Unit = {},
    onEditClickListener: () -> Unit = {},
    onDelClickListener: () -> Unit = {}
) {
    val (Item, setItem) = remember {
        mutableStateOf<LotData?>(null)
    }
    val coroutineScope = rememberCoroutineScope()
    SideEffect {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                setItem(getItemById(lotId))
            } catch (t: Exception) {

            }
        }
    }
    val pagerState = rememberPagerState(pageCount = { Item?.photos?.size ?: 0 })
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(

                title = {
                    Text(
                        text = Item?.name ?: ""
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClickListener() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    if (myLot) {
                        IconButton(onClick = {
                            onEditClickListener()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_pen),
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = {
                            coroutineScope.launch {
                                delItem(Item?.id ?: "")
                                onDelClickListener()
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_trash),
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, top = 64.dp, end = 8.dp, bottom = 4.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize(),
                    pageSpacing = 8.dp
                ) { page ->
                    AsyncImage(
                        model = Item?.photos?.get(page)?.photo,
                        contentDescription = "Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) {
                        val color =
                            if (pagerState.currentPage == it) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(8.dp)
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Характеристики", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))

            CharRow(title = "Категория:", value = Item?.category ?: "")
            CharRow(title = "Состояние:", value = Item?.condition ?: "")

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Описание", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = Item?.description ?: "",
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Местоположение", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = Item?.address ?: "")
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                UserCard(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    myLot = myLot,
                    previousScreen = previousScreen,
                    userName = Item?.user_name ?: "",
                    onUserClickListener = onUserClickListener,
                    userId = Item?.user_id ?: ""
                )
                Spacer(modifier = Modifier.width(4.dp))
                if (!myLot) {
                    Box(
                        modifier = Modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                coroutineScope.launch {

                                    onWriteClickListener(postCreateChat(lotId).id)
                                }

                            }
                        ) {
                            Text(text = "Написать")
                        }
                    }

                }
            }

        }
    }
}

@Composable
fun CharRow(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(text = title, fontSize = 18.sp)
        Text(text = value, fontSize = 18.sp)
    }
}

@Composable
private fun UserCard(
    modifier: Modifier = Modifier,
    myLot: Boolean,
    previousScreen: String = "",
    userName: String,
    userId: String,
    onUserClickListener: (String) -> Unit
) {
    if (previousScreen != "${MainRouteScreen.ElseProfile.route}/${userId}" && !myLot) {
        Card(
            modifier = modifier
                .clickable { onUserClickListener(userId) }
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = userName)
            }
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = userName)
            }
        }
    }

}