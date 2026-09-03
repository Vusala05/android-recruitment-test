package com.example.socketapp.data.util

import android.util.Log
import com.example.socketapp.domain.util.SocketConnectionState
import io.socket.client.Socket
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


@Singleton
class SocketManager @Inject constructor(
    private val socket: Socket
) {

    private val _connectionState =
        MutableStateFlow<SocketConnectionState>(SocketConnectionState.Idle)
    val connectionState: StateFlow<SocketConnectionState> = _connectionState.asStateFlow()

    init {
        socket.on(Socket.EVENT_CONNECT) {
            _connectionState.value = SocketConnectionState.Connected
        }

        socket.on(Socket.EVENT_DISCONNECT) {
            _connectionState.value = SocketConnectionState.Disconnected
        }

        socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
            val errorMsg = args.getOrNull(0)?.toString()
            _connectionState.value = SocketConnectionState.Error(errorMsg)
        }
    }

    fun connect() {

        if (!socket.connected()) {
            _connectionState.value = SocketConnectionState.Connecting
            socket.connect()

        }
    }

    fun disconnect() {
        socket.disconnect()
    }

    fun isConnected(): Boolean = socket.connected()
}