package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class PhotoData(
    var id: String,
    var photo: String
)
