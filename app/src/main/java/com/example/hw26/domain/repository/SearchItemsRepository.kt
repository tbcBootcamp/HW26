package com.example.hw26.domain.repository

import com.example.hw26.data.common.Resource
import com.example.hw26.domain.model.SearchItemDomainModel
import kotlinx.coroutines.flow.Flow

interface SearchItemsRepository {
    suspend fun getSearchItems(): Flow<Resource<List<SearchItemDomainModel>>>
}