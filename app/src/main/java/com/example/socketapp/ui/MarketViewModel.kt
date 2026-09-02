package com.example.socketapp.ui


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socketapp.core.model.ResultWrapper
import com.example.socketapp.domain.useCases.ConnectSocketUseCase
import com.example.socketapp.domain.useCases.DisconnectSocketUseCase
import com.example.socketapp.domain.useCases.GetAllDataUseCase
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
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        connectSocketUseCase()
        observeMarketData()


    }


    private fun observeMarketData() {
        viewModelScope.launch {
            Log.e("DATA","Baslayirr")
            getAllDataUseCase().collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        Log.e("DATA", result.data.toString())
                        _uiState.update {
                            it.copy(marketList = result.data, errorMessage = null)
                        }
                    }
                    is ResultWrapper.Error -> {
                        Log.e("DATA", result.errorMessage.toString())

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

