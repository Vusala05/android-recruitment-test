package com.example.data.domain.response

import com.example.data.domain.util.TrendType
import kotlinx.serialization.Serializable

@Serializable
data class MarketItemResponseDO(
    val trend: TrendType,
    val symbol: String,
    val price: String,
    val highPrice: String,
    val lowPrice: String,
    val openPrice : String,
    val volume : Int,
    val timestamp: String,
    )