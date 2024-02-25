package com.example.hw26.presentation.model

import com.example.hw26.domain.model.SearchItemDomainModel


data class SearchItemUiModel(
    val id: String,
    val name: String,
    val nameDe: String,
    val createdAt: String,
    val bglNumber: String?,
    val bglVariant: String?,
    val orderId: Int?,
    val main: String?,
    val children: List<SearchItemDomainModel>,
    val numberOfChildren: Int
)