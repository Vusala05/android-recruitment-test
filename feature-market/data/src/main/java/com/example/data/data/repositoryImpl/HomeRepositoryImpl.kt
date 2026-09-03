package com.example.data.data.repositoryImpl

import com.example.data.model.ResultWrapper
import com.example.data.data.response.SocketResponse
import com.example.data.domain.repository.HomeRepository
import com.example.data.domain.response.MarketItemResponseDO
import com.example.data.model.handleResultWrapper
import com.example.network.socket.SocketConnectionState
import com.example.network.socket.SocketManager
import com.example.storage.cache.CacheManager
import com.example.storage.cache.getAndConvertToModel
import com.example.storage.cache.writeAndConvertToJson
import com.example.storage.module.CacheModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.serialization.json.Json
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    val socketManager: SocketManager,
    val json: Json,
    @CacheModule.LocalCacheManager val cacheManager: CacheManager
) : HomeRepository {
    override fun getAllData(): Flow<ResultWrapper<List<MarketItemResponseDO>>> {
        var latestData : List<MarketItemResponseDO>?=null
        return flow {
            val cachedData =
                cacheManager.getAndConvertToModel<List<MarketItemResponseDO>>(key = MARKET_ITEM).first()
            if (cachedData != null) {
                emit(ResultWrapper.Success(data = cachedData))

            }
            emitAll(
                socketManager.observeSocketUpdate(event = MESSAGE_EVENT){
                    json.decodeFromString<SocketResponse>(it).result.orEmpty()
                }.map { result ->
                    handleResultWrapper(result = result){ dataList ->
                        dataList.map { it.toDomain()}.also {
                            latestData = it
                        }
                    }
                }
            )
        }
            .onStart { socketManager.connect() }
            .onCompletion {
                latestData?.let {
                    cacheManager.writeAndConvertToJson(MARKET_ITEM,it)
                }
                socketManager.disConnect() }
    }


    override fun observeConnectionState(): Flow<SocketConnectionState> {
        return socketManager.observeSocketState()

    }

    companion object {
        const val MARKET_ITEM = "Market_Item"
        const val MESSAGE_EVENT = "message"
    }
}