package com.funin.todo.network.services

import com.funin.todo.data.vo.TokenAndUser
import com.funin.todo.network.response.NetworkDataResponse
import com.funin.todo.network.response.Response
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("signup")
    suspend fun signUp(@Body body: RequestBody): NetworkDataResponse<TokenAndUser>

    @POST("login")
    suspend fun login(@Body body: RequestBody): NetworkDataResponse<TokenAndUser>
}