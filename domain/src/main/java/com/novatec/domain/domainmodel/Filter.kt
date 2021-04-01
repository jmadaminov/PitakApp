package com.novatec.domain.domainmodel

import java.text.SimpleDateFormat
import java.util.*

/**
 * Representation for a [Filter] fetched from the API
 */



data class Filter(var airConditioner: Boolean? = null,
                  var departureDate: String? = null,
                  var fromDistrictId: Int? = null,
                  var fromRegionId: Int? = null,
                  var toDistrictId: Int? = null,
                  var toRegionId: Int? = null,
                  var maxPrice: Int? = null,
                  var minPrice: Int? = null,
                  var priceOrder: String? = null,
                  var seat: Int? = null,
                  var timeFirstPart: Boolean? = null,
                  var timeSecondPart: Boolean? = null,
                  var timeThirdPart: Boolean? = null,
                  var timeFourthPart: Boolean? = null) {
    companion object {
        const val MAX_PRICE = 500000
        const val MIN_PRICE = 10000
        const val STEP = 10000
    }

}