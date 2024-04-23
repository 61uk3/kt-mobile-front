package com.example.kt_mobile_front.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kt_mobile_front.data.ItemShortLot

@Composable
fun LotCard(
    modifier: Modifier = Modifier,
    itemShortLot: ItemShortLot
){
    Card(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier.weight(1f).background(Color.Red),
            model = itemShortLot.photo,
            contentDescription = null
        )
        Text(text = itemShortLot.name)
    }
}