package com.example.kt_mobile_front.screens

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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.CardChosePhoto
import com.example.kt_mobile_front.components.ImageBox
import com.example.kt_mobile_front.components.MyTextField
import com.example.kt_mobile_front.data.CreateLotData
import com.example.kt_mobile_front.requests.postAddLot
import com.example.kt_mobile_front.requests.postPhotosLot
import kotlinx.coroutines.launch

@Composable
fun AddLotScreen(
    onButtonClickListrner: () -> Unit
) {
    var isImagesError by remember {
        mutableStateOf(false)
    }
    var isTitleError by remember {
        mutableStateOf(false)
    }
    var isDescError by remember {
        mutableStateOf(false)
    }
    var isCondError by remember {
        mutableStateOf(false)
    }
    var isCatError by remember {
        mutableStateOf(false)
    }
    var isStreetError by remember {
        mutableStateOf(false)
    }
    var isHomeError by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val listState = listOf("Новое", "Как новое", "Бу")
    val listCategory = listOf("Инструменты", "Электроника")

    var selectedState by remember {
        mutableStateOf("Состояние")
    }
    var selectedCategory by remember {
        mutableStateOf("Категория")
    }
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var home by remember { mutableStateOf("") }

    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    val multiplePhotosPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = 4
        ),
        onResult = {
            selectedImageUris = it
            isImagesError = false
        }
    )

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 88.dp),
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
        if (isImagesError) {
            item(
                span = { GridItemSpan(2) }
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "Выберите хотя бы одну фотографию", color = Color.Red)
                }
            }
        }
        item(
            span = { GridItemSpan(2) }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = title,
                onValueChange = {
                    title = it
                    isTitleError = false
                },
                label = { Text(text = "Название") },
                maxLines = 1
            )

        }
        if (isTitleError) {
            item(
                span = { GridItemSpan(2) }
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "Укажите название", color = Color.Red)
                }
            }
        }
        item(
            span = { GridItemSpan(2) }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = desc,
                onValueChange = {
                    desc = it
                    isDescError = false
                },
                label = { Text(text = "Описание") }
            )

        }
        if (isDescError) {
            item(
                span = { GridItemSpan(2) }
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "Укажите описание", color = Color.Red)
                }
            }
        }
        item(
            span = { GridItemSpan(2) }
        ) { Spacer(modifier = Modifier.height(2.dp)) }
        item {
            Column {
                DropDown(listState, selectedState) { newState ->
                    selectedState = newState
                    isCondError = false
                }
                if (isCondError) {
                    Text(text = "Выберите состояние", color = Color.Red)
                }
            }
        }
        item {
            Column {
                DropDown(listCategory, selectedCategory) { newCategory ->
                    selectedCategory = newCategory
                    isCatError = false
                }
                if (isCatError) {
                    Text(text = "Выберите категорию", color = Color.Red)
                }
            }

        }
        item(
            span = { GridItemSpan(2) }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    OutlinedTextField(
                        modifier = Modifier,
                        value = street,
                        onValueChange = {
                            street = it
                            isStreetError = false
                        },
                        label = { Text(text = "Улица") }
                    )
                    if (isStreetError) {
                        Text(text = "Укажите улицу", color = Color.Red)
                    }

                }

                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    OutlinedTextField(
                        modifier = Modifier,
                        value = home,
                        onValueChange = {
                            home = it
                            isHomeError = false
                        },
                        label = { Text(text = "Дом") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    if (isHomeError) {
                        Text(text = "Укажите дом", color = Color.Red)
                    }
                }

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
                        if (!selectedImageUris.isEmpty() && title != "" && desc != "" && selectedCategory != "Категория" && selectedState != "Состояние" && street != "" && home != "") {
                            coroutineScope.launch {
                                postAddLot(
                                    createLotData = CreateLotData(
                                        name = title,
                                        description = desc,
                                        address = "$street,$home"
                                    ),
                                    cat = selectedCategory,
                                    cond = selectedState,
                                    uris = selectedImageUris,
                                    context = context
                                )
                            }
                            onButtonClickListrner()
                        } else {
                            if (selectedImageUris.isEmpty()) {
                                isImagesError = true
                            }
                            if (title == "") {
                                isTitleError = true
                            }
                            if (desc == "") {
                                isDescError = true
                            }
                            if (selectedCategory == "Категория") {
                                isCatError = true
                            }
                            if (selectedState == "Состояние") {
                                isCondError = true
                            }
                            if (street == "") {
                                isStreetError = true
                            }
                            if (home == "") {
                                isHomeError = true
                            }
                        }

                    }) {
                    Text(text = "Опубликовать")
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