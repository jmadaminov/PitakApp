package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

data class DriverOfferBody(@SerializedName("postId") val postId: Long,
                           @SerializedName("price") val price: Int? = null,
                           @SerializedName("message") val message: String? = null,
                           @SerializedName("repliedPostId") val repliedPostId: Int? = null)
