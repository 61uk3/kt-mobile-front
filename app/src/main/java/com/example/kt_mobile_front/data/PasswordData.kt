package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class PasswordData(
    var old_pass: String,
    var new_pass: String
)
