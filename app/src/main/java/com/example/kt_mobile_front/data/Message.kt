package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    var id_sender: String,
    var date_send: String,
    var message: String
)
