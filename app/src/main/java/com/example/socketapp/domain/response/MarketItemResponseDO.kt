package com.example.socketapp.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class MarketItemResponseDO(
    val trend: String,
    val symbol: String,
    val price: String,
    val highPrice: String,
    val lowPrice: String,
    val openPrice : String,
    val volume : Int,
    val timestamp: String,
    )
