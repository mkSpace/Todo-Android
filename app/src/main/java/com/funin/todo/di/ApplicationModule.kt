package com.funin.todo.di

import com.funin.todo.data.TodoSharedPreferences
import com.funin.todo.data.TodoSharedPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    fun provideTodoSharedPreferences(): TodoSharedPreferences = TodoSharedPreferencesImpl
}