package com.badcompany.remote.model

import com.badcompany.core.EOfferStatus
import com.badcompany.core.EPostType
import com.google.gson.annotations.SerializedName

data class OfferDTO(@SerializedName("id") val id: Long,
                    @SerializedName("postId") val postId: Long,
                    @SerializedName("offerType") val offerType: EPostType,
                    @SerializedName("profileId") val profileId: Long,
                    @SerializedName("profile") val profileDTO: ProfileDTO,
                    @SerializedName("status") val status: EOfferStatus,
                    @SerializedName("visible") val visible: Boolean,
                    @SerializedName("submitDate") val submitDate: String,
                    @SerializedName("message") val message: String?=null,
                    @SerializedName("price") val price: Int? = null)
