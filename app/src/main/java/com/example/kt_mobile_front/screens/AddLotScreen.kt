package com.example.kt_mobile_front.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun AddLotScreen() {
    var selectedImageUris by remember {
        mutableStateOf<List<Uri?>>(emptyList())
    }
    val multiplePhotosPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = 4
        ),
        onResult = {
            selectedImageUris = it
        }
    )
    Column {
        LazyVerticalGrid(
            modifier = Modifier.padding(4.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(selectedImageUris) { selectedImageUris ->
                ImageBox(
                    modifier = Modifier
                        .size(220.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    selectedImageUris = selectedImageUris,
                    onClickListener = {
                        multiplePhotosPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }
            if(selectedImageUris.size != 4) {
                items(1) {
                    CardChoisePhoto(modifier = Modifier
                        .size(220.dp)
                        .padding(4.dp),
                        onClickListener = {
                            multiplePhotosPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = "",
            onValueChange = {}
        )
    }

}

@Composable
fun PhotoCard(
    modifier: Modifier,
    onClickListener: () -> Unit
){
    Card(
        modifier = modifier,
        onClick = {
            onClickListener()
        }
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Добавить фото")
        }
    }
}

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