package com.example.network.socket

import com.example.data.model.ResultWrapper
import io.socket.client.Socket
import io.socket.emitter.Emitter
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow


@Singleton
class SocketManagerImpl @Inject constructor(
    private val socket: Socket,
) : SocketManager {

    private val _connectionState =
        MutableStateFlow<SocketConnectionState>(SocketConnectionState.Idle)
    val connectionState: StateFlow<SocketConnectionState> = _connectionState.asStateFlow()
    override fun observeSocketState(): StateFlow<SocketConnectionState> {
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
        return connectionState
    }

    override fun connect() {
        if (!socket.connected()) {
            _connectionState.value = SocketConnectionState.Connecting
            socket.connect()

        }
    }


    override fun disConnect() {
        socket.disconnect()
    }


    override fun <T> observeSocketUpdate(
        event: String,
        transform: (String) -> T
    ): Flow<ResultWrapper<T>> = callbackFlow {

        val listener = Emitter.Listener { args ->
            try {
                val rawJson = args[0].toString()
                val dataList = transform(rawJson)
                trySend(ResultWrapper.Success(data = dataList))
            } catch (e: Exception) {
                trySend(ResultWrapper.Error(e.localizedMessage))
                e.printStackTrace()
            }
        }

        socket.on(event, listener)
        awaitClose {
            socket.off(event, listener)
        }
    }

}
