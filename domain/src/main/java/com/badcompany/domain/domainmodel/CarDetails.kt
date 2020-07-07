package com.badcompany.domain.domainmodel

import java.io.Serializable

data class CarDetails(var id: Long? = null,
                      var carModel: IdName? = null,
                      var image: Image? = null,
                      var fuelType: String? = null,
                      var carColor: CarColorBody? = null,
                      var carNumber: String? = null,
                      var carYear: Int? = null,
                      var airConditioner: Boolean? = null,
                      var def: Boolean? = null,
                      var imageList: List<Image>? = null)

data class IdName(var id: Long? = null,
                        var name: String? = null)



