package com.loaizasoftware.data.model

import com.loaizasoftware.domain.models.SignInResponse
import com.loaizasoftware.domain.models.User

data class SignInResponseDto(
    val data: User? = null,
    val message: String,
    val code: Int,
    val token: String
)

fun SignInResponse.toDto() = SignInResponseDto(data, message, code, token)