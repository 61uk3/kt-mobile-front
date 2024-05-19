package com.example.kt_mobile_front.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.CardChosePhoto
import com.example.kt_mobile_front.components.ImageBox
import com.example.kt_mobile_front.components.MyTextField
import com.example.kt_mobile_front.data.CreateLotData
import com.example.kt_mobile_front.data.LotData
import com.example.kt_mobile_front.requests.getItemById
import com.example.kt_mobile_front.requests.putLot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditLotScreen(
    lotId: String,
    onBackClickListener: () -> Unit
) {
    val context = LocalContext.current
    var name by remember {
        mutableStateOf("")
    }
    var desc by remember {
        mutableStateOf("")
    }
    var street by remember {
        mutableStateOf("")
    }
    var home by remember {
        mutableStateOf("")
    }
    var selectedState by remember {
        mutableStateOf("")
    }
    var selectedCategory by remember {
        mutableStateOf("")
    }
    val (Item, setItem) = remember {
        mutableStateOf<LotData?>(null)
    }
    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(listOf())
    }
    val coroutineScope = rememberCoroutineScope()
    /*SideEffect {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                setItem(getItemById(lotId))
                name = Item?.name ?: ""
                desc = Item?.description ?: ""
                street = Item?.address?.split(",", ", ")?.get(1) ?: ""
                home = Item?.address?.split(",", ", ")?.get(2) ?: ""
                selectedState = Item?.condition ?: ""
                selectedCategory = Item?.category ?: ""
                selectedImageUris = selectedImageUris.plus(Item!!.photos.get(0).photo.toUri())
            } catch (_: Exception) {

            }
        }
    }*/
    LaunchedEffect(Unit) {
        try {
            val item = getItemById(lotId)
            name = item.name
            desc = item.description
            val addressParts = item.address.split(",", ", ")
            street = addressParts.getOrNull(1) ?: ""
            home = addressParts.getOrNull(2) ?: ""
            selectedState = item.condition
            selectedCategory = item.category
            selectedImageUris = item.photos.map { it.photo.toUri() }
        } catch (e: Exception) {
            Log.e("EditLotScreen", "Error fetching item by ID: $e")
        }
    }
    val listState = listOf("Новое", "Как новое", "Бу")
    val listCategory = listOf("Инструменты", "Электроника")


    val multiplePhotosPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = 4
        ),
        onResult = {
            selectedImageUris = it
        }
    )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Редактирование") },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClickListener()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, top = 64.dp, end = 8.dp, bottom = 8.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectedImageUris) { selectedImageUris ->
                ImageBox(
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    selectedImageUris = selectedImageUris,
                    onClickListener = {
                        multiplePhotosPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }
            if (selectedImageUris.size != 4) {
                item {
                    CardChosePhoto(modifier = Modifier
                        .size(220.dp),
                        onClickListener = {
                            multiplePhotosPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )
                }
            }
            item(
                span = { GridItemSpan(2) }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = name!!,
                    onValueChange = { name = it },
                    label = { Text(text = "Название") },
                    maxLines = 1
                )

            }

            item(
                span = { GridItemSpan(2) }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text(text = "Описание") }
                )

            }
            item(
                span = { GridItemSpan(2) }
            ) { Spacer(modifier = Modifier.height(2.dp)) }
            item {
                DropDown(listState, selectedState) { newState ->
                    selectedState = newState
                }
            }
            item {
                DropDown(listCategory, selectedCategory) { newCategory ->
                    selectedCategory = newCategory
                }
            }
            item(
                span = { GridItemSpan(2) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier,
                        value = street,
                        onValueChange = { street = it },
                        label = { Text(text = "Улица") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        modifier = Modifier,
                        value = home,
                        onValueChange = { home = it },
                        label = { Text(text = "Дом") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            item(
                span = { GridItemSpan(2) }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                putLot(
                                    id = lotId,
                                    createLotData = CreateLotData(
                                        name = name,
                                        description = desc,
                                        address = "$street,$home"
                                    ),
                                    cat = selectedCategory,
                                    cond = selectedState,
                                    uris = selectedImageUris,
                                    context = context
                                )
                            }
                        }) {
                        Text(text = "Сохранить")
                    }
                }

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropDown(
    list: List<String>,
    selected: String,
    updateSelect: (String) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            OutlinedTextField(
                modifier = Modifier.menuAnchor(),
                value = selected,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                }
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                list.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {
                            updateSelect(list[index])
                            isExpanded = false
                        }
                    )

                }
            }
        }
    }
}