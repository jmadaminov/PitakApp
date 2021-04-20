package com.novatec.remote.mapper

import com.novatec.data.mapper.Mapper
import com.novatec.data.model.PlaceEntity
import com.novatec.remote.model.PlaceModel
import javax.inject.Inject


/**
 * Map a [PlaceEntity] to and from a [PlaceModel] instance when data is moving between
 * this later and the Domain layer
 */
open class PlaceMapper : Mapper<PlaceEntity, PlaceModel> {

    /**
     * Map a [PlaceEntity] instance to a [PlaceModel] instance
     */
    override fun mapFromEntity(type: PlaceEntity): PlaceModel {
        return PlaceModel(type.districtId,
                          type.regionId,
                          type.lat,
                          type.lon,
                          type.regionName,
                          type.districtName,
                          type.name)
    }

    /**
     * Map a [PlaceModel] instance to a [PlaceEntity] instance
     */
    override fun mapToEntity(type: PlaceModel): PlaceEntity {
        return PlaceEntity(type.districtId,
                           type.regionId,
                           type.lat,
                           type.lon,
                           type.regionName,
                           type.districtName,
                           type.name)
    }


}