package com.example.kt_mobile_front.screens

import android.content.Intent
import android.net.Uri
import android.widget.AdapterView.OnItemClickListener
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.ButtonToSite
import com.example.kt_mobile_front.components.MyTextField

@Composable
fun SigninScreen(
    signinClickListener: () -> Unit,
    signupClickListener: () -> Unit
){
    val (login, setLogin) = remember {
        mutableStateOf("")
    }
    val (password, setPassword) = remember {
        mutableStateOf("")
    }
    var passwordHidden by remember {
        mutableStateOf(true)
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
        Spacer(modifier = Modifier.height(64.dp))
        MyTextField(
            label = "Введите логин",
            value = login,
            onValueChange = setLogin,
            leadingIcon = R.drawable.ic_person
        )
        Spacer(modifier = Modifier.height(16.dp))
        MyTextField(
            label = "Введите пароль",
            value = password,
            onValueChange = setPassword,
            keyboardType = KeyboardType.Password,
            leadingIcon = R.drawable.ic_lock,
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
        Button(onClick = { signinClickListener() }) {
            Text(text = "Войти")
        }
        Spacer(modifier = Modifier.height(64.dp))
        val signupText = "Зарегистрироваться"
        val annotatedString = buildAnnotatedString {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)){
                append("Нет аккаунта?")
            }
            append(" ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)){
                pushStringAnnotation(tag = signupText, signupText)
                append(signupText)
            }
        }

        ClickableText(
            annotatedString,
            style = TextStyle(
                fontSize = 16.sp
            )
        ) {offset ->
            annotatedString.getStringAnnotations(offset, offset).forEach {
                if (it.tag == signupText){
                    signupClickListener()
                }
            }
        }
        ButtonToSite()

    }
}