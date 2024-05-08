package com.example.kt_mobile_front.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.CardChosePhoto
import com.example.kt_mobile_front.components.DropDownMenu
import com.example.kt_mobile_front.components.ImageBox
import com.example.kt_mobile_front.components.MyTextField
import com.example.kt_mobile_front.data.SignUpUserData
import com.example.kt_mobile_front.requests.postPhotoSignUp
import com.example.kt_mobile_front.requests.postSignUp
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    loginClickListener: () -> Unit
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    val multiplePhotosPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = 2
        ),
        onResult = {
            selectedImageUris = it
        }
    )
    val (password, setPassword) = remember { mutableStateOf("") }
    val (confirmPassword, setConfirmPassword) = remember { mutableStateOf("") }
    val (name, setName) = remember { mutableStateOf("") }
    val (login, setLogin) = remember { mutableStateOf("") }
    val (phone, setPhone) = remember { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }
    var confirmPasswordHidden by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!selectedImageUris.isEmpty()) {
            ImageBox(
                modifier = Modifier
                    .size(220.dp)
                    .clip(RoundedCornerShape(12.dp)),
                selectedImageUris = selectedImageUris.get(0),
                onClickListener = {
                    multiplePhotosPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
        }
        else {
            CardChosePhoto(modifier = Modifier
                .size(220.dp)
                .padding(4.dp),
                onClickListener = {
                    multiplePhotosPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        MyTextField(
            label = "Имя",
            value = name,
            onValueChange = setName

        )
        Spacer(modifier = Modifier.height(16.dp))
        DropDownMenu()
        Spacer(modifier = Modifier.height(16.dp))
        MyTextField(
            label = "Телефон",
            value = phone,
            onValueChange = setPhone,
            keyboardType = KeyboardType.Phone
        )
        Spacer(modifier = Modifier.height(16.dp))

        MyTextField(
            label = "Логин",
            value = login,
            onValueChange = setLogin
        )
        Spacer(modifier = Modifier.height(16.dp))
        MyTextField(
            label = "Пароль",
            value = password,
            onValueChange = setPassword,
            keyboardType = KeyboardType.Password,
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    if(passwordHidden) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = null
                        )
                    }
                    else
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_eye_crossed),
                            contentDescription = null
                        )
                }
            }
        )


        Spacer(modifier = Modifier.height(16.dp))
        MyTextField(
            label = "Подтвердите пароль",
            value = confirmPassword,
            onValueChange = setConfirmPassword,
            keyboardType = KeyboardType.Password,
            visualTransformation = if (confirmPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { confirmPasswordHidden = !confirmPasswordHidden }) {
                    if(confirmPasswordHidden) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = null
                        )
                    }
                    else
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_eye_crossed),
                            contentDescription = null
                        )
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            try {
                coroutineScope.launch {
                    postSignUp(
                        signUpUserData = SignUpUserData(
                            login, password, name, phone
                        ),
                        "Череповец",
                        uris = selectedImageUris,
                        context = context
                    )
                }
            } catch (ex: Exception){
                Log.d("exxxxxxxxx", ex.message!!)
            }
            loginClickListener()

        }) {
            Text(text = "Зарегистрироваться")
        }
        Spacer(modifier = Modifier.height(32.dp))

        val loginText = "Войти"
        val annotatedString = buildAnnotatedString {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)){
                append("Есть аккаунт?")
            }
            append(" ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)){
                pushStringAnnotation(tag = loginText, loginText)
                append(loginText)
            }
        }

        ClickableText(
            annotatedString,
            style = TextStyle(
                fontSize = 16.sp
            )
        ) {offset ->
            annotatedString.getStringAnnotations(offset, offset).forEach {
                if (it.tag == loginText){
                    loginClickListener()
                }
            }
        }
    }
}