package com.example.hw26.data.mapper

import com.example.hw26.data.model.SearchItemDto
import com.example.hw26.domain.model.SearchItemDomainModel

fun SearchItemDto.toDomain(): SearchItemDomainModel = SearchItemDomainModel(
    id = id,
    name = name,
    nameDe = nameDe,
    createdAt = createdAt,
    bglNumber = bglNumber,
    bglVariant = bglVariant,
    orderId = orderId,
    main = main,
    children = children.map { it.toDomain() }
)
