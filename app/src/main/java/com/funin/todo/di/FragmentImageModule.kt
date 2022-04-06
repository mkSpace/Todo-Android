package com.funin.todo.di

import androidx.fragment.app.Fragment
import com.funin.base.image.GlideApp
import com.funin.base.image.GlideRequests
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentImageModule {

    @Provides
    fun provideGlideRequests(fragment: Fragment): GlideRequests = GlideApp.with(fragment)
}