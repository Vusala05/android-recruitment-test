package com.example.ui

import com.example.data.domain.response.MarketItemResponseDO
import com.example.network.socket.SocketConnectionState

data class HomeUiState(
    val marketList: List<MarketItemResponseDO> = emptyList(),
    val connectionState: SocketConnectionState = SocketConnectionState.Idle,
    val errorMessage: String? = null
) {
    val isConnected: Boolean
        get() = connectionState is SocketConnectionState.Connected
}