package com.badcompany.domain.domainmodel

data class Place(val districtId: Int,
                 val regionId: Int,
                 val nameRu: String? = null,
                 val nameUz: String? = null,
                 val nameEn: String? = null,
                 val lat: Double? = null,
                 val lon: Double? = null)