package com.example.socketapp.data.dataSource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CacheDao {
    @Query("SELECT * FROM CacheTable WHERE dataKey = :key")
    fun getData(key : String) : Flow<CacheEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData (dataEntity : CacheEntity)

    @Query("DELETE FROM CacheTable WHERE dataKey = :key")
    suspend fun removeData(key : String)
}