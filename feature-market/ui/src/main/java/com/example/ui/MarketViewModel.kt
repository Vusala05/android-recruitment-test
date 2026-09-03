package com.example.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.ResultWrapper
import com.example.data.domain.useCases.GetAllDataUseCase
import com.example.data.domain.useCases.ObserveConnectionStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getAllDataUseCase: GetAllDataUseCase,
    private val observeConnectionStateUseCase: ObserveConnectionStateUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<HomeUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
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
                        _uiEffect.emit(HomeUiEffect.ShowMessage(result.errorMessage))
                    }
                }
            }
        }
    }

}

