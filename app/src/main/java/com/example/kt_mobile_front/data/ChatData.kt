package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class ChatData(
    var id: String = "",
    var user_id: String = "",
    var user_name: String = "",
    var user_photo: String = "",
    var lot_id: String = "",
    var lot_photo: String = "",
    var lot_name: String = "",
    var messages: List<Message> = listOf()
)