package com.funin.todo

import android.app.Application
import com.facebook.stetho.Stetho
import com.funin.base.utils.DimensionToPixels
import com.funin.todo.data.TodoSharedPreferencesImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDimensionToPixels()
        initSharedPreferences()
        initStetho()
    }

    private fun initDimensionToPixels() {
        DimensionToPixels.initialize(applicationContext)
    }

    private fun initSharedPreferences() {
        TodoSharedPreferencesImpl.init(applicationContext)
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }
}