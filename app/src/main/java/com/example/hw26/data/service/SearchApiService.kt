package com.example.hw26.data.service

import com.example.hw26.data.model.SearchItemDto
import retrofit2.Response
import retrofit2.http.GET

interface SearchApiService {
    @GET("/v3/6f722f19-3297-4edd-a249-fe765e8104db?search=title")
    suspend fun getSearchItems(): Response<List<SearchItemDto>>
}