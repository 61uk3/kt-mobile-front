package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class ShortChatData(
    var id: String,
    var photo_lots: String,
    var name_lots: String,
    var last_message: String,
    var date: String,
    var sender_name: String
)