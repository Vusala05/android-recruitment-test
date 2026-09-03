package com.example.socketapp.data.repositoryImpl

import com.example.socketapp.core.model.ResultWrapper
import com.example.socketapp.core.model.handleResultWrapper
import com.example.socketapp.data.module.CacheModule
import com.example.socketapp.data.util.SocketManager
import com.example.socketapp.data.util.SocketService
import com.example.socketapp.domain.repository.HomeRepository
import com.example.socketapp.domain.response.MarketItemResponseDO
import com.example.socketapp.domain.util.CacheManager
import com.example.socketapp.domain.util.SocketConnectionState
import com.example.socketapp.domain.util.getAndConvertToModel
import com.example.socketapp.domain.util.writeAndConvertToJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class HomeRepositoryImpl @Inject constructor(
    val socketService: SocketService,
    val socketManager: SocketManager,
    @CacheModule.LocalCacheManager val cacheManager: CacheManager
) : HomeRepository {
        override fun getAllData(): Flow<ResultWrapper<List<MarketItemResponseDO>>> = flow {
            val cachedData = cacheManager.getAndConvertToModel<List<MarketItemResponseDO>>(key = MARKET_ITEM ).first()
                if (cachedData!=null) {
                    emit(ResultWrapper.Success(data = cachedData))

            }
            emitAll(
                socketService.observeMarketUpdates().map { result ->
                    handleResultWrapper(result = result) { dataList ->
                      dataList.map { it.toDomain() }.also {
                            cacheManager.writeAndConvertToJson(key = MARKET_ITEM, value = it)
                      }

                    }
                }
            )
        }

    override fun observeConnectionState(): Flow<SocketConnectionState> {
        return socketManager.connectionState

    }

    override fun connect() {
        return socketManager.connect()
    }

    override fun disconnect() {
        return socketManager.disconnect()
    }
   companion object{
       const val MARKET_ITEM = "Market_Item"
   }
}

