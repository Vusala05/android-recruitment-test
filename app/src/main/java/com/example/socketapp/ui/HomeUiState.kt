package com.example.socketapp.ui

import com.example.socketapp.data.util.SocketConnectionState
import com.example.socketapp.domain.response.MarketItemResponseDO
data class HomeUiState(
    val marketList: List<MarketItemResponseDO> = emptyList(),
    val connectionState: SocketConnectionState = SocketConnectionState.Idle,
    val errorMessage: String? = null
) {
    val isConnected: Boolean
        get() = connectionState is SocketConnectionState.Connected
}