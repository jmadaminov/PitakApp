package com.novatec.data.model

data class ParcelEntity(
    val id: Long,
    val profile: ProfileEntity,
    val submitDate: String,
    val offer: OfferEntity,
    val status: String
)