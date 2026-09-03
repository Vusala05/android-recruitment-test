package com.example.socketapp.data.module

import com.example.socketapp.data.dataSource.LocalCache
import com.example.socketapp.domain.util.CacheManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Qualifier
    annotation class LocalCacheManager

    @LocalCacheManager
    @Binds
    @Singleton
    abstract fun bindLocalCacheManager (localCache: LocalCache) : CacheManager
}