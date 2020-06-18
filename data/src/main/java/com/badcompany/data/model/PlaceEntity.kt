package com.badcompany.data.model

/**
 * Representation for a [PlaceEntity] fetched from an external layer data source
 */
data class PlaceEntity(val districtId: Int,
                       val regionId: Int,
                       val nameRu: String,
                       val nameUz: String)
