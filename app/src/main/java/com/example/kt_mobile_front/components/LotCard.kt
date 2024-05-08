package com.example.kt_mobile_front.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kt_mobile_front.data.ShortLotData

@Composable
fun LotCard(
    modifier: Modifier = Modifier,
    shortLotData: ShortLotData
) {
    Card(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier.weight(1f),
            model = shortLotData.photo,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(text = shortLotData.name, modifier = Modifier.padding(4.dp))
    }
}