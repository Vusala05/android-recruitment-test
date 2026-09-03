package com.example.network.module

import com.example.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import kotlinx.serialization.json.Json
import javax.inject.Singleton

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
        options.path = BuildConfig.SOCKET_PATH
        options.reconnectionAttempts = 0
        options.reconnectionDelay = 5000

        return IO.socket(BuildConfig.SOCKET_URL, options)
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
}