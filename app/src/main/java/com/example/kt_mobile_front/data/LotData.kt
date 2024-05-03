package com.example.kt_mobile_front.data

import kotlinx.serialization.Serializable

@Serializable
data class LotData(
    var id: String,
    var name: String,
    var description: String,
    var date: String,
    var condition: String,
    var category: String,
    var user_name: String,
    var user_id: String,
    var address: String,
    var photos: List<PhotoData>
)
