package com.example.storage.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

suspend inline fun <reified T> CacheManager.writeAndConvertToJson(key : String, value : T) {
    val convertedToString =  Json.encodeToString(value)
    writeData(key,convertedToString)
}
inline fun <reified T> CacheManager.getAndConvertToModel(key : String) : Flow<T?> {
    return getData(key).map {
        try {
            it?.let {
                Json.decodeFromString<T>(it)
            }
        } catch (e : Exception){
           e.printStackTrace()
            null
        }
    }

}