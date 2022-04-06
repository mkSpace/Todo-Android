package com.funin.todo.data

interface TodoSharedPreferences {
    fun getAccessToken(): String?
    fun saveAccessToken(token: String?)
}