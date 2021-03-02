package com.novatec.remote.model

import com.novatec.core.EOfferStatus
import com.novatec.core.EPostType
import com.google.gson.annotations.SerializedName

data class OfferDTO(@SerializedName("id") val id: Long,
                    @SerializedName("postId") val postId: Long,
                    @SerializedName("offerType") val offerType: EPostType,
                    @SerializedName("profileId") val profileId: Long,
                    @SerializedName("profile") val profile: ProfileDTO,
                    @SerializedName("status") val status: EOfferStatus,
                    @SerializedName("visible") val visible: Boolean,
                    @SerializedName("submitDate") val submitDate: String,
                    @SerializedName("message") val message: String?=null,
                    @SerializedName("price") val price: Int? = null,
                    @SerializedName("seat") val seat: Int? = null)
