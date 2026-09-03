package com.example.socketapp.domain.repository

import com.example.socketapp.core.model.ResultWrapper
import com.example.socketapp.data.util.SocketConnectionState
import com.example.socketapp.domain.response.MarketItemResponseDO
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getAllData(): Flow<ResultWrapper<List<MarketItemResponseDO>>>
    fun connect()
    fun disconnect()
}