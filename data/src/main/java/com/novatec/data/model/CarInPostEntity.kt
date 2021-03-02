package com.novatec.data.model

import com.novatec.domain.domainmodel.CarColorBody

data class CarInPostEntity(var id: Long? = null,
                           var modelId: Long? = null,
                           var image: ImageEntity? = null,
                           var carModel: CarModelEntity? = null,
                           var fuelType: String? = null,
                           var color: CarColorEntity? = null,
                           var carNumber: String? = null,
                           var carYear: Int? = null,
                           var airConditioner: Boolean? = null,
                           var def: Boolean? = null)