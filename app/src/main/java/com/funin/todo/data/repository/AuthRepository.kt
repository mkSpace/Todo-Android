package com.funin.todo.data.repository

import com.funin.todo.data.TodoSharedPreferences
import com.funin.todo.data.db.MeDao
import com.funin.todo.data.db.UserDao
import com.funin.todo.data.remote.AuthRemoteDataSource
import com.funin.todo.data.vo.Me
import com.funin.todo.data.vo.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val remote: AuthRemoteDataSource,
    private val userDao: UserDao,
    private val meDao: MeDao,
    private val todoPreferences: TodoSharedPreferences
) {
    suspend fun signup(email: String, nickname: String, password: String) {
        val response = remote.signup(email, nickname, password).data ?: return
        todoPreferences.saveAccessToken(response.accessToken)
        saveMe(response.userId, response.nickname)
    }

    suspend fun login(email: String, password: String) {
        val response = remote.login(email, password).data ?: return
        todoPreferences.saveAccessToken(response.accessToken)
        saveMe(response.userId, response.nickname)
    }

    private fun saveMe(userId: Int, nickname: String) {
        userDao.insert(User(id = userId, nickname = nickname))
        meDao.insert(Me(userId))
    }
}