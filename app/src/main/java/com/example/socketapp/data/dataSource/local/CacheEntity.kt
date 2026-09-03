package com.example.socketapp.data.dataSource.local

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey

@SuppressLint("UnsafeOptInUsageError")
@Entity(tableName = "CacheTable")
data class CacheEntity(
    @PrimaryKey
    val dataKey : String,
    val value : String?
)