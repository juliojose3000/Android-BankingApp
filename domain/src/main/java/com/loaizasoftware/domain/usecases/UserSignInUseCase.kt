package com.loaizasoftware.domain.usecases

import com.loaizasoftware.core.utils.UseCase
import com.loaizasoftware.domain.models.SignInRequest
import com.loaizasoftware.domain.models.SignInResponse
import com.loaizasoftware.domain.repository.UserRepository
import javax.inject.Inject

class UserSignInUseCase @Inject constructor(private val userRepository: UserRepository): UseCase<SignInRequest, Result<SignInResponse>>() {

    override suspend fun invoke(params: SignInRequest): Result<SignInResponse> {
        return userRepository.signIn(params)
    }

}