package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class SignUpUserData(
    var login: String,
    var password: String,
    var name: String,
    var contact: String
)
