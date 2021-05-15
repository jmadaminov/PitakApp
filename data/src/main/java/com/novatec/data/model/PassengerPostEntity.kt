package com.novatec.data.model

import com.novatec.core.EPostStatus
import com.novatec.core.EPostType
import com.novatec.domain.domainmodel.AgreedOffer

/**
 * Representation for a [PassengerPostEntity] fetched from the API
 */
data class PassengerPostEntity(val id: Long,
                               val from: PlaceEntity,
                               val to: PlaceEntity,
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
                               val profile: ProfileEntity,
                               val remark: String? = null,
                               val postStatus: EPostStatus,
                               val myLastOffer: UserOfferEntity?=null,
                               val agreedOffer: AgreedOfferEntity?=null,
                               val seat: Int,
                               val postType: EPostType = EPostType.PASSENGER_SM)

data class UserOfferEntity( val id: Long,
                            val postId: Long,
                            val repliedPostId: Long,
                            val status: String,
                            val message: String,
                            val submitDate: String,
                            val priceInt: Int,
                            val seat: Int)


data class AgreedOfferEntity( val message: String,
                        val priceInt: Int,
                        val seat: Int)