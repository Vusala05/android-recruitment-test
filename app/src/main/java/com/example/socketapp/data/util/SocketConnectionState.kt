package com.example.socketapp.data.util

sealed class SocketConnectionState {

    object Idle : SocketConnectionState()

    object Connecting : SocketConnectionState()

    object Connected : SocketConnectionState()

    object Disconnected : SocketConnectionState()

    data class Error(val message: String?) : SocketConnectionState()
}