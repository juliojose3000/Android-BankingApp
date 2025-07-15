package com.loaizasoftware.data.model

import com.loaizasoftware.domain.models.SignInRequest

data class SignInRequestDto(
    val username: String,
    val password: String
)

fun SignInRequest.toDto() = SignInRequestDto(username, password)