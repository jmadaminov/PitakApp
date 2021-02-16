package com.badcompany.data.model

/**
 * Representation for a [CarEntity] fetched from an external layer data source
 */
data class CarEntity(var id: Long? = null,
                     var modelId: Long? = null,
                     var imageId: Long? = null,
                     var fuelType: String? = null,
                     var colorId: Long? = null,
                     var carNumber: String? = null,
                     var carYear: Int? = null,
                     var airConditioner: Boolean? = null,
                     var def: Boolean? = null,
                     var imageList: List<PhotoEntity>? = null)