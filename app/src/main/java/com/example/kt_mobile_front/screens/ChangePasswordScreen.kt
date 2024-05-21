package com.example.kt_mobile_front.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.MyTextField
import com.example.kt_mobile_front.requests.putPassword
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChangePasswordScreen(
    onBackClickListener: () -> Unit
) {
    var isPasswordEqualsError by remember {
        mutableStateOf(false)
    }
    var isPasswordError by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    var currentPassword by remember {
        mutableStateOf("")
    }
    var newPassword by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    var currentPasswordHidden by remember { mutableStateOf(true) }
    var newPasswordHidden by remember { mutableStateOf(true) }
    var confirmPasswordHidden by remember { mutableStateOf(true) }
    val passEqualsErrorColor =
        if (isPasswordEqualsError) Color.Red else MaterialTheme.colorScheme.background
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Сменить пароль") },
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
                .padding(start = 8.dp, top = 64.dp, end = 8.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Текущий",
                value = currentPassword,
                onValueChange = {
                    currentPassword = it
                },
                keyboardType = KeyboardType.Password,
                visualTransformation = if (currentPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                leadingIcon = R.drawable.ic_lock,
                trailingIcon = {
                    IconButton(onClick = { currentPasswordHidden = !currentPasswordHidden }) {
                        if (currentPasswordHidden) {
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
            Spacer(modifier = Modifier.height(4.dp))
            MyTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Новый",
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    isPasswordEqualsError = false
                    isPasswordError = false
                },
                keyboardType = KeyboardType.Password,
                visualTransformation = if (newPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                leadingIcon = R.drawable.ic_lock,
                trailingIcon = {
                    IconButton(onClick = { newPasswordHidden = !newPasswordHidden }) {
                        if (newPasswordHidden) {
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
                Text(text = "Введите пароль от 6 символов", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(4.dp))
            MyTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Подтверждение",
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    isPasswordEqualsError = false
                },
                keyboardType = KeyboardType.Password,
                visualTransformation = if (confirmPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                leadingIcon = R.drawable.ic_lock,
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
                }
            )
            Text(text = "Пароли не совпадают", color = passEqualsErrorColor)
            Spacer(modifier = Modifier.height(6.dp))
            Button(onClick = {
                if (newPassword == confirmPassword && newPassword.length >= 6) {
                    coroutineScope.launch {
                        putPassword(currentPassword, newPassword)
                    }
                    onBackClickListener()
                } else {
                    if (newPassword != confirmPassword) {
                        isPasswordEqualsError = true
                    }
                    if (newPassword.length < 6) {
                        isPasswordError = true
                    }
                }


            }) {
                Text(text = "Изменить пароль")
            }
        }
    }
}