package com.example.data.domain.repository

import com.example.data.model.ResultWrapper
import com.example.data.domain.response.MarketItemResponseDO
import com.example.network.socket.SocketConnectionState
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getAllData(): Flow<ResultWrapper<List<MarketItemResponseDO>>>

    fun observeConnectionState(): Flow<SocketConnectionState>
}