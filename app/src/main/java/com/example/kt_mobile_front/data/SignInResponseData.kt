package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponseData(
    var access_token: String,
    var token_type: String
)
