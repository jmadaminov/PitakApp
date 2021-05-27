package com.novatec.domain.domainmodel

import com.novatec.core.Constants
import com.novatec.core.EPostStatus
import com.novatec.core.EPostType

data class DriverPost(val id: Long,
                      val from: Place,
                      val to: Place,
                      val price: Int,
                      val departureDate: String,
                      val finishedDate: String? = null,
                      val timeFirstPart: Boolean,
                      val timeSecondPart: Boolean,
                      val timeThirdPart: Boolean,
                      val timeFourthPart: Boolean,
                      val carId: Long? = null,
                      val car: CarInPost? = null,
                      val remark: String? = null,
                      val seat: Int,
                      val offerCount: Int,
                      val passengerCount: Int? = null,
                      val availableSeats: Int,
                      val postStatus: EPostStatus,
                      val pkg: Boolean? = null,
                      val parcelCount: Int = 0,
                      val passengerList: List<Passenger>? = null,
                      val parcelList: List<Parcel>? = null,
                      val postType: EPostType) {
}

