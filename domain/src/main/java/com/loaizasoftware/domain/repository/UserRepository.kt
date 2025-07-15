package com.loaizasoftware.domain.repository

import com.loaizasoftware.domain.models.SignInRequest
import com.loaizasoftware.domain.models.SignInResponse

interface UserRepository {
    suspend fun signIn(request: SignInRequest): SignInResponse
}