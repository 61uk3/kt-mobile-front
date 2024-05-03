package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    var name: String,
    var contact: String,
    var datereg: String,
    var town: TownData,
    var photo: String,
    var items: List<ShortLotData>
)
