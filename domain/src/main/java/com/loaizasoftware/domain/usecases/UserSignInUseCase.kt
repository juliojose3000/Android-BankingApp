package com.loaizasoftware.domain.usecases

import com.loaizasoftware.core.utils.UseCase
import com.loaizasoftware.domain.models.User
import com.loaizasoftware.domain.repository.UserRepository
import javax.inject.Inject

class UserSignInUseCase @Inject constructor(private val userRepository: UserRepository): UseCase<UserSignInUseCase.Params, User>() {

    data class Params(val username: String, val password: String)

    override suspend fun invoke(params: Params): User {
        userRepository.
    }

}