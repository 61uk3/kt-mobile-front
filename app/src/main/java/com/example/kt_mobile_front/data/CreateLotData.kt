package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class CreateLotData(
    var name: String,
    var description: String,
    var address: String
)