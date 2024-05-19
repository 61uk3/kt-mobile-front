package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class PutUserData(
    var name: String,
    var contact: String
)
