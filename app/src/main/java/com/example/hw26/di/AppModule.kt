package com.example.hw26.di

import com.example.hw26.BuildConfig
import com.example.hw26.data.common.HandleResponse
import com.example.hw26.data.service.SearchApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideHandleResponse(): HandleResponse = HandleResponse()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpClint = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpClint.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return httpClint.build()
    }

    @Provides
    @Singleton
    fun provideClothesRetrofitClient(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideClothesApiService(retrofit: Retrofit): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideIODispatcher() = Dispatchers.IO
}