package com.badcompany.data.model

data class CarInPostEntity( var id: Long? = null,
                            var modelId: Long? = null,
                            var image: ImageEntity? = null,
                            var carModel: CarModelEntity? = null,
                            var fuelType: String? = null,
                            var colorId: Long? = null,
                            var carNumber: String? = null,
                            var carYear: Int? = null,
                            var airConditioner: Boolean? = null)