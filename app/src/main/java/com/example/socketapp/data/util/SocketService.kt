package com.example.socketapp.data.util

import android.util.Log
import com.example.socketapp.core.model.ResultWrapper
import com.example.socketapp.data.response.MarketItemResponse
import com.example.socketapp.data.response.SocketResponse
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SocketService @Inject constructor(
    private val socket: Socket,
    private val json: Json
) {

    companion object {
        private const val EVENT_MESSAGE = "message"
    }

    fun observeMarketUpdates(): Flow<ResultWrapper<List<MarketItemResponse>>> = callbackFlow {
        val listener = Emitter.Listener { args ->
            try {
                val rawJson = args[0].toString()
                val response = json.decodeFromString<SocketResponse>(rawJson)
                val dataList = response.result.orEmpty()
                trySend(ResultWrapper.Success(data = dataList))
            } catch (e: Exception) {
                trySend(ResultWrapper.Error(e.localizedMessage))
                e.printStackTrace()
            }
        }

        socket.on(EVENT_MESSAGE, listener)


        awaitClose {
            socket.off(EVENT_MESSAGE, listener)
        }
    }
}