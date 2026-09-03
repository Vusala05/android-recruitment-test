package com.example.ui

sealed interface HomeUiEffect {
    data class ShowMessage (val message: String?) : HomeUiEffect
}