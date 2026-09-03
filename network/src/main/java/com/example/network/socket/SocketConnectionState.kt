package com.example.network.socket

sealed interface SocketConnectionState {

    object Idle : SocketConnectionState

    object Connecting : SocketConnectionState

    object Connected : SocketConnectionState

    object Disconnected : SocketConnectionState

    data class Error(val message: String?) : SocketConnectionState
}