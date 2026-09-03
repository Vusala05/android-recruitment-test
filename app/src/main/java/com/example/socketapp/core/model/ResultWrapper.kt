package com.example.socketapp.core.model

sealed interface ResultWrapper<out T> {
    data class Success<T>(val data : T) : ResultWrapper<T>
    data class Error(val errorMessage : String?) : ResultWrapper<Nothing>
}

inline fun <reified T, reified R> handleResultWrapper(
    result : ResultWrapper<T>,
    transform : (T) -> R
) : ResultWrapper<R> {
    return when(result){
        is ResultWrapper.Error -> ResultWrapper.Error(
            errorMessage = result.errorMessage
        )
        is ResultWrapper.Success ->
            try {
                ResultWrapper.Success(data = transform(result.data))
            } catch (e: Exception){
                e.printStackTrace()
                ResultWrapper.Error(
                    errorMessage = e.localizedMessage ?: "UNKNOWN ERROR"
                )
            }
    }
}