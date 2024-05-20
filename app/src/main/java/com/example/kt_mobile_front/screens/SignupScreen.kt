package com.example.kt_mobile_front.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.CardChosePhoto
import com.example.kt_mobile_front.components.DropDownMenu
import com.example.kt_mobile_front.components.ImageBox
import com.example.kt_mobile_front.components.MyTextField
import com.example.kt_mobile_front.data.SignUpUserData
import com.example.kt_mobile_front.requests.postSignUp
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    loginClickListener: () -> Unit
) {
    var isLoginError by remember {
        mutableStateOf(false)
    }
    var isNameError by remember {
        mutableStateOf(false)
    }
    var isImageError by remember {
        mutableStateOf(false)
    }
    var isPhoneError by remember {
        mutableStateOf(false)
    }
    var isPasswordError by remember {
        mutableStateOf(false)
    }
    var isPasswordEqualsError by remember {
        mutableStateOf(false)
    }
    var isTownError by remember {
        mutableStateOf(false)
    }
    var response by remember {
        mutableStateOf("")
    }
    val listTown = listOf("Череповец", "Шексна", "Вологда")
    var town by remember {
        mutableStateOf("Город")
    }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    var selectedImageUris1 by remember {
        mutableStateOf<Uri?>(null)
    }
    val multiplePhotosPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            selectedImageUris1 = it
            selectedImageUris = emptyList()
            selectedImageUris = selectedImageUris.plus(it!!)
            isImageError = false
        }
    )
    var phone by remember {
        mutableStateOf("")
    }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var login by rememberSaveable { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }
    var confirmPasswordHidden by remember { mutableStateOf(true) }
    val imageErrorColor = if (isImageError) Color.Red else MaterialTheme.colorScheme.background
    val nameErrorColor = if (isNameError) Color.Red else MaterialTheme.colorScheme.background
    val townErrorColor = if (isTownError) Color.Red else MaterialTheme.colorScheme.background
    val phoneErrorColor = if (isPhoneError || response.drop(1)
            .dropLast(1) == "contact"
    ) Color.Red else MaterialTheme.colorScheme.background
    val loginErrorColor = if (isLoginError || response.drop(1)
            .dropLast(1) == "login"
    ) Color.Red else MaterialTheme.colorScheme.background
    val passwordErrorColor =
        if (isPasswordError) Color.Red else MaterialTheme.colorScheme.background
    val passEqualsErrorColor =
        if (isPasswordEqualsError) Color.Red else MaterialTheme.colorScheme.background
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedImageUris1 != null) {
            ImageBox(
                modifier = Modifier
                    .size(220.dp)
                    .clip(RoundedCornerShape(12.dp)),
                selectedImageUris = selectedImageUris1,
                onClickListener = {
                    multiplePhotosPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
        } else {
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

        Text(text = "Выберите фотографию", color = imageErrorColor)

        Spacer(modifier = Modifier.height(2.dp))
        MyTextField(
            label = "Имя",
            value = name,
            onValueChange = {
                name = it
                if (name.length < 2) {
                    isNameError = true
                } else {
                    isNameError = false
                }
            },
            singleLine = true
        )

        Text(text = "Введите имя от 2 символов", color = nameErrorColor)

        Spacer(modifier = Modifier.height(2.dp))
        DropDownMenu(
            listTown,
            town
        ) {
            town = it
            isTownError = false
        }
        Text(text = "Выберите город", color = townErrorColor)
        Spacer(modifier = Modifier.height(2.dp))
        PhoneField(phone,
            mask = "+70000000000",
            maskNumber = '0',
            onPhoneChanged = {
                phone = it
                isPhoneError = false
                response = ""
            }
        )
        val phoneErrorText =
            if (response.drop(1).dropLast(1) != "contact") "Введите номер" else "Номер существует"
        Text(text = phoneErrorText, color = phoneErrorColor)
        Spacer(modifier = Modifier.height(2.dp))

        MyTextField(
            label = "Логин",
            value = login,
            onValueChange = {
                login = it
                isLoginError = false
                response = ""
            },
            singleLine = true
        )
        val loginErrorText = if (response.drop(1)
                .dropLast(1) == "login"
        ) "Логин существует" else "Введите логин от 6 символов"
        Text(text = loginErrorText, color = loginErrorColor)
        Spacer(modifier = Modifier.height(2.dp))
        MyTextField(
            label = "Пароль",
            value = password,
            onValueChange = {
                password = it
                if (password.length < 6) {
                    isPasswordError = true
                } else {
                    isPasswordError = false
                }
            },
            keyboardType = KeyboardType.Password,
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    if (passwordHidden) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = null
                        )
                    } else
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_eye_crossed),
                            contentDescription = null
                        )
                }
            },
            singleLine = true
        )
        Text(text = "Введите пароль от 6 символов", color = passwordErrorColor)

        Spacer(modifier = Modifier.height(2.dp))
        MyTextField(
            label = "Подтвердите пароль",
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                isPasswordEqualsError = false
            },
            keyboardType = KeyboardType.Password,
            visualTransformation = if (confirmPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { confirmPasswordHidden = !confirmPasswordHidden }) {
                    if (confirmPasswordHidden) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = null
                        )
                    } else
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_eye_crossed),
                            contentDescription = null
                        )
                }
            },
            singleLine = true
        )
        Text(text = "Пароли не совпадают", color = passEqualsErrorColor)

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (login.length >= 6 && name.length >= 2 && password.length >= 6 && password == confirmPassword && phone.length == 10 && !selectedImageUris.isEmpty() && town != "Город") {
                try {
                    coroutineScope.launch {
                        response = postSignUp(
                            signUpUserData = SignUpUserData(
                                login, password, name, phone
                            ),
                            "Череповец",
                            uris = selectedImageUris,
                            context = context
                        )
                        if (response.dropLast(1).drop(1) == "login") {
                            isLoginError = true
                        }
                        if (response.drop(1).dropLast(1) == "contact") {
                            isPhoneError = true
                        }
                        if (response.drop(1).dropLast(1) != "login" && response.drop(1)
                                .dropLast(1) != "contact"
                        ) {
                            loginClickListener()
                        }

                    }
                } catch (_: Exception) {
                }

            } else {
                if (login.length < 6) {
                    isLoginError = true
                }
                if (name.length < 2) {
                    isNameError = true
                }
                if (password.length < 6) {
                    isPasswordError = true
                }
                if (password != confirmPassword) {
                    isPasswordEqualsError = true
                }
                if (phone.length < 10) {
                    isPhoneError = true
                }
                if (selectedImageUris.isEmpty()) {
                    isImageError = true
                }
                if (town == "Город") {
                    isTownError = true
                }
            }


        }) {
            Text(text = "Зарегистрироваться")
        }
        Spacer(modifier = Modifier.height(16.dp))

        val loginText = "Войти"
        val annotatedString = buildAnnotatedString {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                append("Есть аккаунт?")
            }
            append(" ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                pushStringAnnotation(tag = loginText, loginText)
                append(loginText)
            }
        }

        ClickableText(
            annotatedString,
            style = TextStyle(
                fontSize = 16.sp
            )
        ) { offset ->
            annotatedString.getStringAnnotations(offset, offset).forEach {
                if (it.tag == loginText) {
                    loginClickListener()
                }
            }
        }
    }
}

@Composable
fun PhoneField(
    phone: String,
    modifier: Modifier = Modifier,
    mask: String = "000 000 00 00",
    maskNumber: Char = '0',
    onPhoneChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = phone,
        onValueChange = { it ->
            onPhoneChanged(it.take(mask.count { it == maskNumber }))
        },
        label = {
            Text(text = "Номер телефона")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = PhoneVisualTransformation(mask, maskNumber),
        leadingIcon = { }
    )
}

class PhoneVisualTransformation(val mask: String, val maskNumber: Char) : VisualTransformation {

    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneVisualTransformation) return false
        if (mask != other.mask) return false
        if (maskNumber != other.maskNumber) return false
        return true
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}