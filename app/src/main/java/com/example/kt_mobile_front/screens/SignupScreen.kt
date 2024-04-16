package com.example.kt_mobile_front.screens

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.AsyncImage
import com.example.kt_mobile_front.R
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun SignupScreen(
    loginClickListener: () -> Unit
){
    var selectedImageUris by remember {
        mutableStateOf<Uri?>(null)
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
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
        }
        else {
            CardChoisePhoto(modifier = Modifier
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
        Button(onClick = { /*TODO*/ }) {
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

@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    label: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: Int? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    readOnly: Boolean = false
){
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label ?: "") },
        leadingIcon = {
            if (leadingIcon != null)
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null
                )
        },
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        readOnly = readOnly
    )
}

@Composable
fun CardChoisePhoto(
    modifier: Modifier,
    onClickListener: () -> Unit
){
    PhotoCard(
        modifier = modifier,
        onClickListener = {
            onClickListener()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropDownMenu(){
    val list = listOf("Череповец", "Вологда", "Шексна")

    var selectedText by remember {
        mutableStateOf("Город")
    }

    var isExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded}
        ) {
            MyTextField(
                modifier = Modifier.menuAnchor(),
                value = selectedText,
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
                list.forEachIndexed{ index, text ->
                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {
                            selectedText = list[index]
                            isExpanded = false
                        }
                    )

                }
            }
        }
    }
}