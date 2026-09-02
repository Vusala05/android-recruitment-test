package com.example.socketapp.domain.useCases

import com.example.socketapp.core.model.ResultWrapper
import com.example.socketapp.domain.repository.HomeRepository
import com.example.socketapp.domain.response.MarketItemResponseDO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDataUseCase @Inject constructor(
    val homeRepository: HomeRepository
) {
    operator fun invoke() : Flow<ResultWrapper<List<MarketItemResponseDO>>>{
        return homeRepository.getAllData()
    }
}