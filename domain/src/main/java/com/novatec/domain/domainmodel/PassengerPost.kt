package com.novatec.domain.domainmodel

import com.novatec.core.EPostStatus
import com.novatec.core.EPostType

/**
 * Representation for a [PassengerPost] fetched from the API
 */
data class PassengerPost(val id: Long,
                         val from: Place,
                         val to: Place,
                         val price: Int,
                         val departureDate: String,
                         val createdDate: String,
                         val updatedDate: String,
                         val finishedDate: String? = null,
                         val timeFirstPart: Boolean,
                         val timeSecondPart: Boolean,
                         val timeThirdPart: Boolean,
                         val timeFourthPart: Boolean,
                         val airConditioner: Boolean,
                         val profile: Profile,
                         val remark: String?=null,
                         val postStatus: EPostStatus,
                         val myLastOffer: UserOffer?=null,
                         val seat: Int,
                         val agreedOffer: AgreedOffer?=null,
                         val postType: EPostType = EPostType.PASSENGER_SM)


data class UserOffer( val id: Long,
                            val postId: Long,
                            val repliedPostId: Long,
                            val status: String,
                            val message: String,
                            val submitDate: String,
                            val priceInt: Int,
                            val seat: Int)


data class AgreedOffer( val message: String,
                        val priceInt: Int,
                        val seat: Int)