package com.example.hw26.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw26.data.common.Resource
import com.example.hw26.domain.usecase.GetFilterItemsUseCase
import com.example.hw26.presentation.event.SearchEvent
import com.example.hw26.presentation.mapper.toPresentation
import com.example.hw26.presentation.model.SearchItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchItemViewModel @Inject constructor(private val searchItemsUseCase: GetFilterItemsUseCase) :
    ViewModel() {
    private val _searchItemsStateFlow = MutableStateFlow(SearchItemState())
    val searchItemsStateFlow = _searchItemsStateFlow.asStateFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.GetSearchedItemsEvent -> getSearchedItems(filter = event.filter)
            is SearchEvent.ResetError ->setError(null)
        }
    }

    private fun getSearchedItems(filter: String) {
        viewModelScope.launch {
            searchItemsUseCase(filter).collect { resource ->
                when (resource) {
                    is Resource.Success -> _searchItemsStateFlow.update { currentState ->
                        currentState.copy(
                            filteredItems = resource.data.map { it.toPresentation() })
                    }

                    is Resource.Loading -> _searchItemsStateFlow.update { currentState ->
                        currentState.copy(
                            isLoading = resource.loading
                        )
                    }

                    is Resource.Error -> {
                        setError(resource.error)
                    }
                }
            }
        }
    }

    private fun setError(error: String?) {
        viewModelScope.launch {
            _searchItemsStateFlow.update { currentState -> currentState.copy(error = error) }
        }
    }
}

data class SearchItemState(
    val filteredItems: List<SearchItemUiModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
