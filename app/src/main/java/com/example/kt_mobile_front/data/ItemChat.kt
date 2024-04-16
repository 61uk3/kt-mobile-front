package com.example.kt_mobile_front.data

import com.example.kt_mobile_front.R

data class ItemChat(
    val id: Int,
    val lotImageId: Int = R.drawable.login_image,
    val lotName: String = "Lot",
    val userName: String = "User",
    val userAvatarId: Int = R.drawable.user_avatar,
    val messageText: String = "Message",
    val messageTime: String = "12.02.24"
)
