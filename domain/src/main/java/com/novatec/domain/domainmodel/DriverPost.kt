package com.novatec.domain.domainmodel

import com.novatec.core.Constants
import com.novatec.core.EPostStatus

data class DriverPost(val id: Long? = null,
                      val from: Place,
                      val to: Place,
                      val price: Int,
                      val departureDate: String,
                      val finishedDate: String?=null,
                      val timeFirstPart: Boolean,
                      val timeSecondPart: Boolean,
                      val timeThirdPart: Boolean,
                      val timeFourthPart: Boolean,
                      val carId: Long?=null,
                      val car: CarInPost?=null,
                      val remark: String?=null,
                      val seat: Int,
                      val offerCount: Int,
                      val availableSeats: Int?=null,
                      val postStatus: EPostStatus,
                      val pkg: Boolean?=null,
                      val passengerList: List<Passenger>?=null,
                      val postType: String = Constants.DRIVER_POST_SIMPLE)

