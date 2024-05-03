package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class TownData(
    var id: String,
    var town: String
)
