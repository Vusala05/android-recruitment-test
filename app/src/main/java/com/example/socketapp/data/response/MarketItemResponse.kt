package com.example.socketapp.data.response

import android.annotation.SuppressLint
import com.example.socketapp.domain.response.MarketItemResponseDO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@SuppressLint("UnsafeOptInUsageError")
@Serializable
 data class MarketItemResponse(
    @SerialName("0")
    val trend: String? = null,

    @SerialName("1")
    val symbol: String? = null,

    @SerialName("2")
    val price: String? = null,

    @SerialName("3")
    val highPrice: String? = null,

    @SerialName("4")
    val lowPrice: String? = null,

    @SerialName("5")
    val openPrice: String? = null,

    @SerialName("6")
    val volume: Int? = null,

    @SerialName("7")
    val timestamp: String? = null
){
        fun toDomain() : MarketItemResponseDO{
            return MarketItemResponseDO(
                trend = this.trend.orEmpty(),
                symbol = this.symbol.orEmpty(),
                price = this.symbol.orEmpty(),
                highPrice = this.highPrice.orEmpty(),
                lowPrice = this.lowPrice.orEmpty(),
                openPrice = this.openPrice.orEmpty(),
                volume = this.volume ?:0,
                timestamp = this.timestamp.orEmpty()
            )
        }

}
