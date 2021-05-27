package com.novatec.data.model

import com.novatec.core.EPostStatus
import com.novatec.core.EPostType

data class DriverPostEntity(val id: Long,
                            val from: PlaceEntity,
                            val to: PlaceEntity,
                            val price: Int,
                            val departureDate: String,
                            val finishedDate: String? = null,
                            val timeFirstPart: Boolean,
                            val timeSecondPart: Boolean,
                            val timeThirdPart: Boolean,
                            val timeFourthPart: Boolean,
                            val carId: Long? = null,
                            val car: CarInPostEntity? = null,
                            val remark: String? = null,
                            val seat: Int,
                            val offerCount: Int,
                            val passengerCount: Int? = null,
                            val availableSeats: Int,
                            val postStatus: EPostStatus,
                            val pkg: Boolean? = null,
                            val parcelCount: Int,
                            val passengerList: List<PassengerEntity>? = null,
                            val parcelList: List<ParcelEntity>? = null,
                            val postType: EPostType)

