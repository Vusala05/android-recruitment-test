package com.example.data.data.module

import com.example.data.data.repositoryImpl.HomeRepositoryImpl
import com.example.data.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository (homeRepositoryImpl: HomeRepositoryImpl) : HomeRepository
}