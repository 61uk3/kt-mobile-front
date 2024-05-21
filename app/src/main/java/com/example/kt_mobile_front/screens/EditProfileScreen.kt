package com.example.kt_mobile_front.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.CardChosePhoto
import com.example.kt_mobile_front.components.DropDownMenu
import com.example.kt_mobile_front.components.ImageBox
import com.example.kt_mobile_front.components.MyTextField
import com.example.kt_mobile_front.data.PutUserData
import com.example.kt_mobile_front.data.UserData
import com.example.kt_mobile_front.requests.getUser
import com.example.kt_mobile_front.requests.putUser
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditProfileScreen(
    onBackClickListener: () -> Unit
) {
    var isNameError by remember {
        mutableStateOf(false)
    }
    var isImageError by remember {
        mutableStateOf(false)
    }
    var isPhoneError by remember {
        mutableStateOf(false)
    }
    var isTownError by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val listTown = listOf("Череповец", "Шексна", "Вологда")
    var town by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var selectedImageUris by remember {
        mutableStateOf<Uri?>(null)
    }
    var selectedImagesUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        try {
            val user = getUser()
            name = user.name
            phone = user.contact
            town = user.town.town
            selectedImageUris = user.photo.toUri()
        } catch (_: Exception) {

        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            selectedImageUris = it
            selectedImagesUris = emptyList()
            selectedImagesUris = selectedImagesUris.plus(it!!)
        }
    )
    val nameErrorColor = if (isNameError) Color.Red else MaterialTheme.colorScheme.background
    val phoneErrorColor = if (isPhoneError) Color.Red else MaterialTheme.colorScheme.background
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Редактирование профиля") },
                navigationIcon = {
                    IconButton(onClick = { onBackClickListener() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (selectedImageUris != null) {
                ImageBox(
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    selectedImageUris = selectedImageUris,
                    onClickListener = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            } else {
                CardChosePhoto(modifier = Modifier
                    .size(220.dp)
                    .padding(4.dp),
                    onClickListener = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            MyTextField(
                label = "Имя",
                value = name,
                onValueChange = {
                    name = it
                    isNameError = false
                }
            )
            if (isNameError) {
                Text(text = "Введите имя от 2 символов", color = nameErrorColor)
            }
            Spacer(modifier = Modifier.height(16.dp))
            DropDownMenu(
                listTown,
                town
            ) {
                town = it
            }
            Spacer(modifier = Modifier.height(16.dp))
            PhoneField(phone,
                mask = "+70000000000",
                maskNumber = '0',
                onPhoneChanged = {
                    phone = it
                    isPhoneError = false
                }
            )
            if (isPhoneError ){
                Text(text = "Введите номер", color = phoneErrorColor)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                if (phone.length == 10 && !selectedImagesUris.isEmpty() && name.length >= 2) {
                    coroutineScope.launch {
                        putUser(
                            PutUserData(
                                name,
                                phone
                            ),
                            town,
                            selectedImagesUris,
                            context
                        )
                    }
                } else {
                    if (name.length < 2) {
                        isNameError = true
                    }
                    if (phone.length < 10) {
                        isPhoneError = true
                    }
                    if (selectedImagesUris.isEmpty()) {
                        isImageError = true
                    }
                }

            }) {
                Text(text = "Сохранить")
            }
        }
    }

}