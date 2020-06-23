package com.badcompany.data.mapper

import com.badcompany.data.model.PlaceEntity
import com.badcompany.domain.domainmodel.Place
import javax.inject.Inject


/**
 * Map a [PlaceEntity] to and from a [Place] instance when data is moving between
 * this later and the Domain layer
 */
open class PlaceMapper @Inject constructor() : Mapper<PlaceEntity, Place> {

    /**
     * Map a [PlaceEntity] instance to a [Place] instance
     */
    override fun mapFromEntity(type: PlaceEntity): Place {
        return Place(type.districtId, type.regionId, type.nameRu, type.nameUz, type.nameEn, type.lat, type.lon)
    }

    /**
     * Map a [Place] instance to a [PlaceEntity] instance
     */
    override fun mapToEntity(type: Place): PlaceEntity {
        return PlaceEntity(type.districtId, type.regionId, type.nameRu, type.nameUz, type.nameEn, type.lat, type.lon)
    }


}