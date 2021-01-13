package com.badcompany.domain.domainmodel

import com.badcompany.core.EPostType

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
                         val postStatus: String,
                         val seat: Int,
                         val postType: EPostType = EPostType.PASSENGER_SM)