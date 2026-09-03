package com.example.storage.cache

import com.example.storage.room.CacheDao
import com.example.storage.room.CacheEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalCache @Inject constructor(val dao: CacheDao) : CacheManager {
    override suspend fun writeData(
            key: String,
            value: String,
        ) {
            dao.insertData(
                CacheEntity(
                    key,
                    value,
                )
            )
        }

        override fun getData(key: String): Flow<String?> {
            return  dao.getData(key).map { it?.value}

        }



    }