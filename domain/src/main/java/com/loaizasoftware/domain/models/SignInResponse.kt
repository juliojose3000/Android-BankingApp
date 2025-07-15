package com.loaizasoftware.domain.models

data class SignInResponse(
    val data: User? = null,
    val message: String,
    val code: Int,
    val token: String
)