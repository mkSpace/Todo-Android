package com.funin.base.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TodoRetrofit {

    const val BASE_URL = "http://ec2-3-36-247-44.ap-northeast-2.compute.amazonaws.com/api/v1/"

    fun <T> create(
        service: Class<T>,
        client: OkHttpClient,
        httpUrl: String = BASE_URL
    ): T = Retrofit.Builder()
        .baseUrl(httpUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(service)

    inline fun <reified T : Any> create(
        client: OkHttpClient,
        httpUrl: String = BASE_URL
    ): T {
        require(httpUrl.isNotBlank()) { "Parameter httpUrl cannot be blank." }
        return create(service = T::class.java, client = client, httpUrl = httpUrl)
    }
}