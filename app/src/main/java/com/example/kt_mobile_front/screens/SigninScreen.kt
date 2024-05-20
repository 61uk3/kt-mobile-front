package com.example.kt_mobile_front.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.ButtonToSite
import com.example.kt_mobile_front.components.MyTextField
import com.example.kt_mobile_front.data.SignInData
import com.example.kt_mobile_front.requests.postSignIn
import kotlinx.coroutines.launch

@Composable
fun SigninScreen(
    signinClickListener: () -> Unit,
    signupClickListener: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var login by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordHidden by remember {
        mutableStateOf(true)
    }
    var isError by remember {
        mutableStateOf(false)
    }
    var isLoginError by remember {
        mutableStateOf(false)
    }
    var isPasswordError by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(300.dp),
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = null
        )

        Text(text = "Добро пожаловать", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Войдите в аккаунт")
        Spacer(modifier = Modifier.height(24.dp))
        if (isError) {
            Text(text = "Неверный логин или пароль!", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(24.dp))
        MyTextField(
            singleLine = true,
            label = "Введите логин",
            value = login,
            onValueChange = {
                login = it
                isError = false
                isLoginError = false
            },
            leadingIcon = R.drawable.ic_person
        )
        if (isLoginError) {
            Text(text = "Заполните поле", color = Color.Red)
        }
        if (!isLoginError) {
            Spacer(modifier = Modifier.height(16.dp))
        }

        MyTextField(
            singleLine = true,
            label = "Введите пароль",
            value = password,
            onValueChange = {
                password = it
                isError = false
                isPasswordError = false
            },
            keyboardType = KeyboardType.Password,
            leadingIcon = R.drawable.ic_lock,
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
            }
        )
        if (isPasswordError) {
            Text(text = "Заполните поле", color = Color.Red)
        }
        if (!isPasswordError) {
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(onClick = {
            coroutineScope.launch {
                if (login != "" && password != "") {
                    try {
                        postSignIn(signInData = SignInData(login, password))
                        signinClickListener()
                    } catch (e: Exception) {
                        isError = true
                    }
                } else {
                    if (login == "") {
                        isLoginError = true
                    }
                    if (password == "") {
                        isPasswordError = true
                    }
                }

            }
        }) {
            Text(text = "Войти")
        }
        Spacer(modifier = Modifier.height(64.dp))
        val signupText = "Зарегистрироваться"
        val annotatedString = buildAnnotatedString {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                append("Нет аккаунта?")
            }
            append(" ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                pushStringAnnotation(tag = signupText, signupText)
                append(signupText)
            }
        }

        ClickableText(
            annotatedString,
            style = TextStyle(
                fontSize = 16.sp
            )
        ) { offset ->
            annotatedString.getStringAnnotations(offset, offset).forEach {
                if (it.tag == signupText) {
                    signupClickListener()
                }
            }
        }
        ButtonToSite()

    }
}