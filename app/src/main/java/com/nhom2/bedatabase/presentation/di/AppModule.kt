package com.nhom2.bedatabase.presentation.di

import android.content.Context
import com.nhom2.bedatabase.data.api.ApiService
import com.nhom2.bedatabase.data.api.RetrofitAPI
import com.nhom2.bedatabase.data.prefs.Pref
import com.nhom2.bedatabase.data.repository.MainRepositoryImpl
import com.nhom2.bedatabase.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePref(
        @ApplicationContext context: Context
    ): Pref = Pref(context)

    @Singleton
    @Provides
    fun provideApiService(): ApiService = RetrofitAPI.service

    @Singleton
    @Provides
    fun provideMainRepository(
        pref: Pref,
        api: ApiService
    ): MainRepository = MainRepositoryImpl(pref = pref, api = api)

}