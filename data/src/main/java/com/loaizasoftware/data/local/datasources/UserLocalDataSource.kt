package com.loaizasoftware.data.local.datasources

import com.loaizasoftware.data.local.dao.UserDao
import com.loaizasoftware.data.local.entity.toDomain
import com.loaizasoftware.data.local.entity.toEntity
import com.loaizasoftware.domain.models.SignInRequest
import com.loaizasoftware.domain.models.SignInResponse
import com.loaizasoftware.domain.models.User
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(private val userDao: UserDao) {

    suspend fun signIn(request: SignInRequest): SignInResponse {
        val userEntity = userDao.authenticate(request.username, request.password)

        return if (userEntity != null) {
            SignInResponse(
                data = userEntity.toDomain(),
                message = "User authenticated successfully",
                code = 200,
                token = generateMockToken() // This should ideally be from backend
            )
        } else {
            SignInResponse(
                data = null,
                message = "Invalid credentials",
                code = 401,
                token = ""
            )
        }
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

    private fun generateMockToken(): String {
        return "mock-token-1234" // replace with real token logic if needed
    }
}
