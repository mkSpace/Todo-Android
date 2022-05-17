package com.funin.todo.data.repository

import com.funin.todo.data.TodoSharedPreferences
import com.funin.todo.data.db.MeDao
import com.funin.todo.data.db.UserDao
import com.funin.todo.data.remote.AuthRemoteDataSource
import com.funin.todo.data.vo.Me
import com.funin.todo.data.vo.User
import com.funin.todo.network.response.onErrorReturnDataNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val remote: AuthRemoteDataSource,
    private val userDao: UserDao,
    private val meDao: MeDao,
    private val todoPreferences: TodoSharedPreferences
) {
    suspend fun signup(email: String, nickname: String, password: String): Boolean {
        val response =
            remote.signup(email, nickname, password).onErrorReturnDataNull() ?: return false
        todoPreferences.saveAccessToken(response.accessToken)
        saveMe(response.userId, response.nickname)
        return true
    }

    suspend fun login(email: String, password: String): Boolean {
        val response = remote.login(email, password).onErrorReturnDataNull() ?: return false
        todoPreferences.saveAccessToken(response.accessToken)
        saveMe(response.userId, response.nickname)
        return true
    }

    private fun saveMe(userId: Int, nickname: String) {
        userDao.insert(User(id = userId, nickname = nickname))
        meDao.insert(Me(userId))
    }
}