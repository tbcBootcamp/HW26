package com.example.hw26.domain.usecase

import com.example.hw26.data.common.Resource
import com.example.hw26.domain.model.SearchItemDomainModel
import com.example.hw26.domain.repository.SearchItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GetFilterItemsUseCase @Inject constructor(private val searchItemsRepository: SearchItemsRepository) {

    suspend operator fun invoke(filterName: String) = withContext(Dispatchers.IO) {
        return@withContext flow<Resource<List<SearchItemDomainModel>>> {
            searchItemsRepository.getSearchItems().collect {
                when(it) {
                    is Resource.Success -> emit(Resource.Success(data = filterData(it.data, filterName)))
                    is Resource.Loading -> emit(Resource.Loading(loading = it.loading))
                    is Resource.Error -> emit(Resource.Error(error = it.error))
                }
            }
        }

    }

    private fun filterData(items: List<SearchItemDomainModel>, searchTerm: String): MutableList<SearchItemDomainModel> {
        val filteredItems = mutableListOf<SearchItemDomainModel>()
        for (item in items) {
            if (item.name.contains(searchTerm, ignoreCase = true)) {
                filteredItems.add(item.copy(numberOfChildren = assignDepth(item)))
            }
            filteredItems.addAll(filterData(item.children, searchTerm))
        }
        return filteredItems
    }

    private fun assignDepth(searchItem: SearchItemDomainModel): Int {
        return if (searchItem.children.isEmpty()) {
            0
        } else {
            val childDepths = searchItem.children.map { assignDepth(it) }
            (childDepths.maxOrNull() ?: 0) + 1
        }
    }
}