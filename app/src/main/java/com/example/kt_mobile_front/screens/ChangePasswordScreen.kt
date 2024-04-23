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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.kt_mobile_front.R
import com.example.kt_mobile_front.components.MyTextField

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChangePasswordScreen(){
    val (currentPassword, setCurrentPassword) = remember {
        mutableStateOf("")
    }
    val (newPassword, setNewPassword) = remember {
        mutableStateOf("")
    }
    val (confirmPassword, setConfirmPassword) = remember {
        mutableStateOf("")
    }
    var currentPasswordHidden by remember { mutableStateOf(true) }
    var newPasswordHidden by remember { mutableStateOf(true) }
    var confirmPasswordHidden by remember { mutableStateOf(true) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Сменить пароль") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = null)
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
                onValueChange = setCurrentPassword,
                keyboardType = KeyboardType.Password,
                visualTransformation = if (currentPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                leadingIcon = R.drawable.ic_lock,
                trailingIcon = {
                    IconButton(onClick = { currentPasswordHidden = !currentPasswordHidden }) {
                        if(currentPasswordHidden) {
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
            Spacer(modifier = Modifier.height(4.dp))
            MyTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Новый",
                value = newPassword,
                onValueChange = setNewPassword,
                keyboardType = KeyboardType.Password,
                visualTransformation = if (newPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                leadingIcon = R.drawable.ic_lock,
                trailingIcon = {
                    IconButton(onClick = { newPasswordHidden = !newPasswordHidden }) {
                        if(newPasswordHidden) {
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
            Spacer(modifier = Modifier.height(4.dp))
            MyTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Подтверждение",
                value = confirmPassword,
                onValueChange = setConfirmPassword,
                keyboardType = KeyboardType.Password,
                visualTransformation = if (confirmPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                leadingIcon = R.drawable.ic_lock,
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
            Spacer(modifier = Modifier.height(6.dp))
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Изменить пароль")
            }
        }
    }
}