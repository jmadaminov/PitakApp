package com.badcompany.remote.mapper

import com.badcompany.data.mapper.Mapper
import com.badcompany.data.model.PlaceEntity
import com.badcompany.data.model.UserCredentialsEntity
import com.badcompany.data.model.UserEntity
import com.badcompany.remote.model.PlaceModel
import com.badcompany.remote.model.UserCredentialsModel
import com.badcompany.remote.model.UserInfoModel
import com.badcompany.remote.model.UserModel
import javax.inject.Inject


/**
 * Map a [PlaceEntity] to and from a [PlaceModel] instance when data is moving between
 * this later and the Domain layer
 */
open class PlaceMapper @Inject constructor() : Mapper<PlaceEntity, PlaceModel> {

    /**
     * Map a [PlaceEntity] instance to a [PlaceModel] instance
     */
    override fun mapFromEntity(type: PlaceEntity): PlaceModel {
        return PlaceModel(type.districtId, type.regionId, type.lat, type.lon, type.regionName, type.districtName)
    }

    /**
     * Map a [PlaceModel] instance to a [PlaceEntity] instance
     */
    override fun mapToEntity(type: PlaceModel): PlaceEntity {
        return PlaceEntity(type.districtId, type.regionId, type.lat, type.lon, type.regionName, type.districtName)
    }


}