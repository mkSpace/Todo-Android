package com.funin.todo.di

import android.content.Context
import com.funin.todo.data.db.MeDao
import com.funin.todo.data.db.TodoDatabase
import com.funin.todo.data.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideTodoDatabase(@ApplicationContext context: Context) =
        TodoDatabase.getInstance(context)

    @Provides
    fun provideUserDao(todoDatabase: TodoDatabase): UserDao = todoDatabase.userDao()

    @Provides
    fun provideMeDao(todoDatabase: TodoDatabase): MeDao = todoDatabase.meDao()
}