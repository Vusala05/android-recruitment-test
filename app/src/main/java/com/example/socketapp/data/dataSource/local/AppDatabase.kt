package com.example.socketapp.data.dataSource.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CacheEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cacheDao() : CacheDao
}