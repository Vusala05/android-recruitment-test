package com.example.socketapp.data.module

import com.example.socketapp.core.constants.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object SocketModule {

    @Provides
    @Singleton
    fun provideWebSocketClient(): io.socket.client.Socket {
        val options = IO.Options()
        options.transports = arrayOf("websocket")
        options.upgrade = true
        options.forceNew = true
        options.reconnection = true
        options.path = ApiConstants.SOCKET_PATH
        options.reconnectionAttempts = 0
        options.reconnectionDelay = 5000

        return IO.socket(ApiConstants.SOCKET_URL, options)
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
}