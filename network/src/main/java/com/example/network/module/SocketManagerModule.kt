package com.example.network.module

import com.example.network.socket.SocketManager
import com.example.network.socket.SocketManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SocketManagerModule {

    @Binds
    @Singleton
    abstract fun bindSocketManager (socketManagerImpl: SocketManagerImpl) : SocketManager
}