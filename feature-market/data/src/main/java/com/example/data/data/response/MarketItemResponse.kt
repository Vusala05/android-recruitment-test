package com.example.data.data.response

import android.annotation.SuppressLint
import com.example.data.domain.response.MarketItemResponseDO
import com.example.data.domain.util.TrendType
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
        fun toDomain() : MarketItemResponseDO {
            return MarketItemResponseDO(
                trend = TrendType.fromString(this.trend ?:""),
                symbol = this.symbol.orEmpty(),
                price = this.price.orEmpty(),
                highPrice = this.highPrice.orEmpty(),
                lowPrice = this.lowPrice.orEmpty(),
                openPrice = this.openPrice.orEmpty(),
                volume = this.volume ?:0,
                timestamp = this.timestamp.orEmpty()
            )
        }

}
