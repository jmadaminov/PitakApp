package com.badcompany.domain.domainmodel
import com.badcompany.core.Constants

/**
 * Representation for a [PassengerPostModel] fetched from the API
 */
data class PassengerPostModel( val id: Long?=null,
                               val from: Place,
                               val to: Place,
                               val price: Int,
                               val departureDate: String,
                               val createdDate: String,
                               val updatedDate: String,
                               val finishedDate: String?=null,
                               val timeFirstPart: Boolean,
                               val timeSecondPart: Boolean,
                               val timeThirdPart: Boolean,
                               val timeFourthPart: Boolean,
                               val airConditioner: Boolean,
                               val remark: String,
                               val postStatus: String,
                               val seat: Int,
                               val postType: String = Constants.PASSENGER_POST_SIMPLE)