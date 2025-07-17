package com.loaizasoftware.domain.models

import com.loaizasoftware.core.utils.UiState

data class SignInResponse(
    val data: User? = null,
    val isSuccessful: Boolean,
    val message: String,
    val code: Int,
    val token: String
)