package com.example.socketapp.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socketapp.core.model.ResultWrapper
import com.example.socketapp.domain.useCases.ConnectSocketUseCase
import com.example.socketapp.domain.useCases.DisconnectSocketUseCase
import com.example.socketapp.domain.useCases.GetAllDataUseCase
import com.example.socketapp.domain.useCases.ObserveConnectionStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getAllDataUseCase: GetAllDataUseCase,
    private val connectSocketUseCase: ConnectSocketUseCase,
    private val disconnectSocketUseCase: DisconnectSocketUseCase,
    private val observeConnectionStateUseCase: ObserveConnectionStateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        connectSocketUseCase()
        observeConnectionState()
         observeMarketData()

    }
    private fun observeConnectionState(){
        viewModelScope.launch {
            observeConnectionStateUseCase().collect { connectionState ->
            _uiState.update { it.copy(connectionState = connectionState) }
            }

        }
    }

    private fun observeMarketData() {
        viewModelScope.launch {
            getAllDataUseCase().collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.update {
                            it.copy(marketList = result.data, errorMessage = null)
                        }
                    }
                    is ResultWrapper.Error -> {

                        _uiState.update {
                            it.copy(errorMessage = result.errorMessage)
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnectSocketUseCase()
    }
}

