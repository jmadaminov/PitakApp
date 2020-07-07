package com.badcompany.data.model

/**
 * Representation for a [CarDetailsEntity] fetched from an external layer data source
 */
data class CarDetailsEntity(var id: Long? = null,
                            var carModel: IdNameEntity? = null,
                            var image: ImageEntity? = null,
                            var fuelType: String? = null,
                            var carColor: CarColorEntity? = null,
                            var carNumber: String? = null,
                            var carYear: Int? = null,
                            var airConditioner: Boolean? = null,
                            var def: Boolean? = null,
                            var imageList: List<ImageEntity>? = null)

data class IdNameEntity(var id: Long? = null,
                        var name: String? = null)