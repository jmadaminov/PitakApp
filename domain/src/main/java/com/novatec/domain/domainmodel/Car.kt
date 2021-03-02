package com.novatec.domain.domainmodel

data class Car(var id: Long? = null,
               var modelId: Long? = null,
               var imageId: Long? = null,
               var fuelType: String? = null,
               var colorId: Long? = null,
               var carNumber: String? = null,
               var carYear: Int? = null,
               var airConditioner: Boolean = false,
               var def: Boolean = false,
               var imageList: List<PhotoBody> = arrayListOf())

