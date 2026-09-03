package com.example.network.socket

import com.example.data.model.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SocketManager {

    fun observeSocketState(): StateFlow<SocketConnectionState>
    fun connect()
    fun disConnect()
    fun <T> observeSocketUpdate ( event : String, transform :(String) -> T) : Flow<ResultWrapper<T>>
}