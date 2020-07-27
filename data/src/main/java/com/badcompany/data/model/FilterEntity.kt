package com.badcompany.data.model

/**
 * Representation for a [FilterEntity] fetched from the API
 */
data class FilterEntity(var airConditioner: Boolean? = null,
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
                        var timeFourthPart: Boolean? = null)