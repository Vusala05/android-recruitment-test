package com.example.socketapp.domain.util

import kotlinx.coroutines.flow.Flow

interface CacheManager  {

    suspend fun writeData( key : String, value : String)

    fun getData ( key : String) : Flow<String?>

}