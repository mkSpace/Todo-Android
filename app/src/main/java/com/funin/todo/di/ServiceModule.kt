package com.funin.todo.di

import com.funin.base.network.TodoRetrofit
import com.funin.todo.network.services.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    fun provideAuthService(okHttpClient: OkHttpClient): AuthService =
        TodoRetrofit.create(okHttpClient)
}