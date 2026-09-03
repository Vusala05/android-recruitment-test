package com.example.socketapp.data.dataSource

import com.example.socketapp.data.dataSource.local.CacheDao
import com.example.socketapp.data.dataSource.local.CacheEntity
import com.example.socketapp.domain.util.CacheManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalCache @Inject constructor(val dao: CacheDao) : CacheManager {
    override suspend fun writeData(
            key: String,
            value: String,
        ) {
            dao.insertData(CacheEntity(
                    key,
                    value,
                )
            )
        }

        override fun getData(key: String): Flow<String?> {
            return  dao.getData(key).map { it?.value}

        }



    }
