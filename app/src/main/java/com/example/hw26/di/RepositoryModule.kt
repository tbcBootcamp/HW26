package com.example.hw26.di

import com.example.hw26.data.common.HandleResponse
import com.example.hw26.data.repository.SearchItemsRepositoryImpl
import com.example.hw26.data.service.SearchApiService
import com.example.hw26.domain.repository.SearchItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideSearchItemsRepository(searchApiService: SearchApiService, handleResponse: HandleResponse): SearchItemsRepository =
        SearchItemsRepositoryImpl(
            apiService = searchApiService,
            handleResponse = handleResponse,
        )
}