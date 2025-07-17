package com.loaizasoftware.data.repository

import com.loaizasoftware.data.local.datasources.UserLocalDataSource
import com.loaizasoftware.domain.models.SignInRequest
import com.loaizasoftware.domain.models.SignInResponse
import com.loaizasoftware.domain.repository.UserRepository
import org.jetbrains.annotations.Async.Execute
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dataSource: UserLocalDataSource): UserRepository {

    override suspend fun signIn(request: SignInRequest): Result<SignInResponse> {

        val response = dataSource.signIn(request)
        return if(response.isSuccessful) {
            Result.success(response)
        } else {
            Result.failure(Exception(response.message))
        }

    }

}