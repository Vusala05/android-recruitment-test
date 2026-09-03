package com.example.data.domain.useCases

import com.example.data.domain.repository.HomeRepository
import com.example.network.socket.SocketConnectionState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ObserveConnectionStateUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(): Flow<SocketConnectionState> = repository.observeConnectionState()
}
