package com.novatec.remote.model

import com.google.gson.annotations.SerializedName

data class ProfileDTO(
    @SerializedName("id") val id: String,
    @SerializedName("phoneNum") val phoneNum: String,
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("image") val image: ImageDTO? = null)
