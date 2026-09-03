package com.example.data.domain.useCases

import com.example.data.model.ResultWrapper
import com.example.data.domain.repository.HomeRepository
import com.example.data.domain.response.MarketItemResponseDO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDataUseCase @Inject constructor(
    val homeRepository: HomeRepository
) {
    operator fun invoke() : Flow<ResultWrapper<List<MarketItemResponseDO>>>{
        return homeRepository.getAllData()
    }
}