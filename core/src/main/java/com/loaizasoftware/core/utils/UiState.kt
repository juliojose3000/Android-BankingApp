package com.loaizasoftware.core.utils

sealed class UiState {
    data object Loading: UiState()
    data class Success(val data: Any): UiState()
    data class Error(val error: String): UiState()
}