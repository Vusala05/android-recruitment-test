package com.example.socketapp.data.repositoryImpl

import com.example.socketapp.core.model.ResultWrapper
import com.example.socketapp.core.model.handleResultWrapper
import com.example.socketapp.data.util.SocketManager
import com.example.socketapp.data.util.SocketService
import com.example.socketapp.domain.repository.HomeRepository
import com.example.socketapp.domain.response.MarketItemResponseDO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    val socketService: SocketService,
    val socketManager: SocketManager
) : HomeRepository {
        override fun getAllData(): Flow<ResultWrapper<List<MarketItemResponseDO>>> {
            return socketService.observeMarketUpdates()
                .map { result ->
                    handleResultWrapper(result = result) { dataList ->
                        dataList.map { it.toDomain() }
                    }
                }
        }

    override fun connect() {
        return socketManager.connect()
    }

    override fun disconnect() {
        return socketManager.disconnect()
    }

}
