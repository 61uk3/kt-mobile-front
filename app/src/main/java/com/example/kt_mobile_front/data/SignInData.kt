package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class SignInData(
    var login: String,
    var password: String
)