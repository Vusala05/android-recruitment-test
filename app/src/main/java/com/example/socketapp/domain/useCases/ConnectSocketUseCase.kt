package com.example.socketapp.domain.useCases

import com.example.socketapp.domain.repository.HomeRepository
import javax.inject.Inject

class ConnectSocketUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke() = repository.connect()
}