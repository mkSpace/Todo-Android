package com.funin.todo.network.interceptors

import com.funin.todo.data.TodoSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val preferences: TodoSharedPreferences
): Interceptor {
    companion object {
        private const val KEY_AUTHORIZATION = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
            .newBuilder()
            .apply {
                runBlocking(Dispatchers.IO) {
                    val token = preferences.getAccessToken() ?: return@runBlocking
                    header(KEY_AUTHORIZATION, token)
                }
            }
            .build()
    )
}