package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class ShortLotData(
    var id: String,
    var name: String,
    var photo: String,
    var town: String,
    var condition: String,
    var category: String
)