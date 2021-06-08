package com.novatec.remote.model

import com.google.gson.annotations.SerializedName
import com.novatec.core.EOfferStatus
import com.novatec.core.EPostType

data class OfferDTO(@SerializedName("id") val id: Long? = null,
                    @SerializedName("postId") val postId: Long? = null,
                    @SerializedName("offerType") val offerType: EPostType? = null,
                    @SerializedName("senderId") val profileId: Long? = null,
                    @SerializedName("senderProfile") val profile: ProfileDTO? = null,
                    @SerializedName("status") val status: EOfferStatus? = null,
                    @SerializedName("submitDate") val submitDate: String? = null,
                    @SerializedName("message") val message: String? = null,
                    @SerializedName("price") val price: Int? = null,
                    @SerializedName("seat") val seat: Int? = null)
