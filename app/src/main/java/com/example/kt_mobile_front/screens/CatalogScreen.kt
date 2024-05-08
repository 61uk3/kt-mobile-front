package com.example.kt_mobile_front.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.LotCard
import com.example.kt_mobile_front.data.ShortLotData
import com.example.kt_mobile_front.requests.getAllItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onLotClickListener: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val lotList = mutableListOf<ShortLotData>()
    SideEffect {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                lotList.apply {
                    getAllItems().forEach {
                        add(it)
                    }
                }
            } catch (t: Exception) {

            }
        }
    }

    fun search(
        text: String
    ): List<ShortLotData> {
        return lotList.filter {
            it.name.lowercase().startsWith(text.lowercase())
        }
    }

    val mainList = remember {
        mutableStateOf(lotList)
    }

    val searchText = remember {
        mutableStateOf("")
    }
    var isActive by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .height(76.dp)
                    .padding(horizontal = 8.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(6f)
                ) {
                    SearchBar(
                        query = searchText.value,
                        onQueryChange = {
                            searchText.value = it
                        },
                        onSearch = {
                             isActive = false
                             mainList.value = search(it).toMutableList()
                        },
                        active = isActive,
                        onActiveChange = {
                             isActive = it
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null
                            )
                        },
                        placeholder = {
                            Text(text = "Поиск...", fontSize = 20.sp)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {

                    }

                }
                Box(
                    Modifier
                        .height(64.dp)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        if (mainList.value.isEmpty() || (searchText.value != "" && mainList.value.isEmpty())){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 4.dp, top = 72.dp, end = 4.dp, bottom = 88.dp)
            ) {
                items(mainList.value){
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

    }
}