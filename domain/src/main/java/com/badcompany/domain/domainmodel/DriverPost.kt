package com.badcompany.domain.domainmodel

import com.badcompany.core.Constants

data class DriverPost(val id: Long? = null,
                      val from: Place,
                      val to: Place,
                      val price: Int,
                      val departureDate: String,
                      val timeFirstPart: Boolean,
                      val timeSecondPart: Boolean,
                      val timeThirdPart: Boolean,
                      val timeFourthPart: Boolean,
                      val carId: Long,
                      val remark: String,
                      val seat: Int,
                      val postType: String = Constants.DRIVER_POST_SIMPLE)

