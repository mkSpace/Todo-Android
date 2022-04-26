package com.funin.todo.data.remote

import com.funin.base.extensions.requestBodyOf
import com.funin.todo.data.vo.TokenAndUser
import com.funin.todo.network.response.NetworkDataResponse
import com.funin.todo.network.services.AuthService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSource @Inject constructor(private val service: AuthService) {

    suspend fun signup(
        email: String,
        nickname: String,
        password: String
    ): NetworkDataResponse<TokenAndUser> = service.signUp(
        requestBodyOf {
            "email" to email
            "nickname" to nickname
            "password" to password
        }
    )

    suspend fun login(
        email: String,
        password: String
    ): NetworkDataResponse<TokenAndUser> = service.login(
        requestBodyOf {
            "email" to email
            "password" to password
        }
    )
}