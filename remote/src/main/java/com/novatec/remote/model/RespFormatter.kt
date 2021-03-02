package com.novatec.remote.model

import com.google.gson.annotations.SerializedName

 data class RespFormatter<T>(@SerializedName("code") val code: Int? = null,
                           @SerializedName("message") val message: String? = null,
                           @SerializedName("data") val data: T? = null)