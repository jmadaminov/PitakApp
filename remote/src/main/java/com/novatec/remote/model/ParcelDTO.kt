package com.novatec.remote.model

import com.google.gson.annotations.SerializedName

data class ParcelDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("profile") val profile: ProfileDTO,
    @SerializedName("submitDate") val submitDate: String,
    @SerializedName("offer") val offer: OfferDTO,
    @SerializedName("status") val status: String,
)
