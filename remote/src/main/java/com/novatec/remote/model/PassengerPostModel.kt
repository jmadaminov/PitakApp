package com.novatec.remote.model

import com.google.gson.annotations.SerializedName
import com.novatec.core.EPostStatus
import com.novatec.core.EPostType

/**
 * Representation for a [PassengerPostModel] fetched from the API
 */
data class PassengerPostModel(@SerializedName("id") val id: Long,
                              @SerializedName("from") val from: PlaceModel,
                              @SerializedName("to") val to: PlaceModel,
                              @SerializedName("price") val price: Int,
                              @SerializedName("departureDate") val departureDate: String,
                              @SerializedName("createdDate") val createdDate: String,
                              @SerializedName("updatedDate") val updatedDate: String,
                              @SerializedName("finishedDate") val finishedDate: String? = null,
                              @SerializedName("timeFirstPart") val timeFirstPart: Boolean,
                              @SerializedName("timeSecondPart") val timeSecondPart: Boolean,
                              @SerializedName("timeThirdPart") val timeThirdPart: Boolean,
                              @SerializedName("timeFourthPart") val timeFourthPart: Boolean,
                              @SerializedName("airConditioner") val airConditioner: Boolean,
                              @SerializedName("profile") val profile: ProfileDTO,
                              @SerializedName("remark") val remark: String? = null,
                              @SerializedName("postStatus") val postStatus: EPostStatus,
                              @SerializedName("seat") val seat: Int,
                              @SerializedName("myOffer") val myLastOffer: UserOfferDTO? = null,
                              @SerializedName("offer") val agreedOffer: AgreedOfferDTO? = null,
                              @SerializedName("imageList") val imageList: List<Image> = listOf(),
                              @SerializedName("postType") val postType: EPostType = EPostType.PASSENGER_SM) {
}


data class UserOfferDTO(@SerializedName("id") val id: Long,
                        @SerializedName("postId") val postId: Long,
                        @SerializedName("repliedPostId") val repliedPostId: Long,
                        @SerializedName("status") val status: String,
                        @SerializedName("message") val message: String,
                        @SerializedName("submitDate") val submitDate: String,
                        @SerializedName("priceInt") val priceInt: Int,
                        @SerializedName("seat") val seat: Int)


data class AgreedOfferDTO(@SerializedName("message") val message: String,
                          @SerializedName("priceInt") val priceInt: Int,
                          @SerializedName("seat") val seat: Int)
