package com.example.socketapp.data.response

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SocketResponse(
    @SerialName("result")
    val result: List<MarketItemResponse>? = null
)


