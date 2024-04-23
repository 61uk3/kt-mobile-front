package com.example.kt_mobile_front.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kt_mobile_front.R

@Composable
fun ImageBox(
    modifier: Modifier = Modifier,
    selectedImageUris: Uri?,
    onClickListener: () -> Unit
){
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .clickable {
                    onClickListener()
                },
            model = selectedImageUris,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier
            .padding(6.dp)
            .clip(CircleShape)
            .alpha(0.7f)
            .background(color = MaterialTheme.colorScheme.background)
            .align(alignment = Alignment.BottomEnd)
        ){
            Icon(
                modifier = Modifier
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.ic_pen),
                contentDescription = null
            )
        }
    }
}