package com.example.movieproject.di

import com.example.movieproject.repository.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ApiServices{
        return retrofit.create(ApiServices::class.java)
    }
}