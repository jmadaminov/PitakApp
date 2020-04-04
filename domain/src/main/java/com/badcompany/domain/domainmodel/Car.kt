package com.badcompany.domain.domainmodel

data class Car(var id: String,
               var model: String,
               var photoUrl: String,
               var fuelType: String,
               var color: String,
               var productionYear: String,
               var hasAirConditioning: Boolean? = null)

