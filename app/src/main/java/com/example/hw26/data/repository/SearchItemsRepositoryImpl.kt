package com.example.hw26.data.repository

import com.example.hw26.data.common.HandleResponse
import com.example.hw26.data.common.Resource
import com.example.hw26.data.mapper.base.asResource
import com.example.hw26.data.mapper.toDomain
import com.example.hw26.data.service.SearchApiService
import com.example.hw26.domain.model.SearchItemDomainModel
import com.example.hw26.domain.repository.SearchItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchItemsRepositoryImpl @Inject constructor(private val apiService: SearchApiService, private val handleResponse: HandleResponse):
    SearchItemsRepository {
    override suspend fun getSearchItems(): Flow<Resource<List<SearchItemDomainModel>>> {
        return handleResponse.safeApiCall {
            apiService.getSearchItems()
        }.asResource {
            it.map { it.toDomain() }
        }
    }
}