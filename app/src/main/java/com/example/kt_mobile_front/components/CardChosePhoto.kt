package com.example.kt_mobile_front.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CardChosePhoto(
    modifier: Modifier,
    onClickListener: () -> Unit
){
    AddPhotoCard(
        modifier = modifier,
        onClickListener = {
            onClickListener()
        }
    )
}