package com.example.socketapp.domain.useCases

import com.example.socketapp.domain.util.SocketConnectionState
import com.example.socketapp.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ObserveConnectionStateUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(): Flow<SocketConnectionState> = repository.observeConnectionState()
}
