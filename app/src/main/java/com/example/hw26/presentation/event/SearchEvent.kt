package com.example.hw26.presentation.event

sealed class SearchEvent {
    data object ResetError : SearchEvent()
    data class GetSearchedItemsEvent(val filter: String) : SearchEvent()
}