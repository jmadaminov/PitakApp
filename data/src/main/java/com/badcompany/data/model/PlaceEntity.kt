package com.badcompany.data.model

/**
 * Representation for a [PlaceEntity] fetched from an external layer data source
 */
data class PlaceEntity(val districtId: Int? = null,
                       val regionId: Int? = null,
                       val lat: Double? = null,
                       val lon: Double? = null,
                       val regionName: String? = null,
                       val districtName: String? = null,
                       val name: String? = null
)
