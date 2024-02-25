package com.example.hw26.presentation.mapper

import com.example.hw26.domain.model.SearchItemDomainModel
import com.example.hw26.presentation.model.SearchItemUiModel


fun SearchItemDomainModel.toPresentation() = SearchItemUiModel(
    id = id,
    name = name,
    nameDe = nameDe,
    createdAt = createdAt,
    bglNumber = bglNumber,
    bglVariant = bglVariant,
    orderId = orderId,
    main = main,
    children = children.map { it },
    numberOfChildren = numberOfChildren
)
