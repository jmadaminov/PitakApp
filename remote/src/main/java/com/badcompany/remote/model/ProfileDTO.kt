package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

data class ProfileDTO(@SerializedName("phoneNum") val phoneNum: String,
                      @SerializedName("name") val name: String,
                      @SerializedName("surname") val surname: String,
                      @SerializedName("id") val id: String,
                      @SerializedName("image") val image: Image? = null)
