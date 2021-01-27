package com.badcompany.data.model

import com.badcompany.core.Constants
import com.badcompany.core.EPostStatus

data class DriverPostEntity(val id: Long? = null,
                            val from: PlaceEntity,
                            val to: PlaceEntity,
                            val price: Int,
                            val departureDate: String,
                            val finishedDate: String?=null,
                            val timeFirstPart: Boolean,
                            val timeSecondPart: Boolean,
                            val timeThirdPart: Boolean,
                            val timeFourthPart: Boolean,
                            val carId: Long?=null,
                            val car: CarInPostEntity?=null,
                            val remark: String?=null,
                            val seat: Int,
                            val availableSeats: Int?=null,
                            val postStatus: EPostStatus,
                            val pkg: Boolean?=null,
                            val passengerList: List<PassengerEntity>?=null,
                            val postType: String = Constants.DRIVER_POST_SIMPLE)

