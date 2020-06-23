package com.badcompany.remote.mapper

import com.badcompany.data.mapper.Mapper
import com.badcompany.data.model.DriverPostEntity
import com.badcompany.data.model.PlaceEntity
import com.badcompany.remote.model.DriverPostModel
import com.badcompany.remote.model.PlaceModel
import javax.inject.Inject


/**
 * Map a [DriverPostEntity] to and from a [DriverPostModel] instance when data is moving between
 * this later and the Domain layer
 */
open class DriverPostMapper @Inject constructor() : Mapper<DriverPostEntity, DriverPostModel> {

    /**
     * Map a [DriverPostEntity] instance to a [DriverPostModel] instance
     */
    override fun mapFromEntity(type: DriverPostEntity): DriverPostModel {

        val placeFrom = PlaceModel(type.from.districtId,
                                   type.from.regionId,
                                   type.from.nameRu,
                                   type.from.nameUz,
                                   type.from.nameEn,
                                   type.from.lat,
                                   type.from.lon)

        val placeTo = PlaceModel(type.to.districtId,
                                 type.to.regionId,
                                 type.to.nameRu,
                                 type.to.nameUz,
                                 type.to.nameEn,
                                 type.to.lat,
                                 type.to.lon)

        return DriverPostModel(placeFrom,
                               placeTo,
                               type.price,
                               type.departureDate,
                               type.timeFirstPart,
                               type.timeSecondPart,
                               type.timeThirdPart,
                               type.timeFourthPart,
                               type.carId,
                               type.remark,
                               type.seat,
                               type.postType)
    }

    /**
     * Map a [DriverPostModel] instance to a [DriverPostEntity] instance
     */
    override fun mapToEntity(type: DriverPostModel): DriverPostEntity {

        val placeFrom = PlaceEntity(type.from.districtId,
                                   type.from.regionId,
                                   type.from.nameRu,
                                   type.from.nameUz,
                                   type.from.nameEn,
                                   type.from.lat,
                                   type.from.lon)

        val placeTo = PlaceEntity(type.to.districtId,
                                       type.to.regionId,
                                       type.to.nameRu,
                                       type.to.nameUz,
                                       type.to.nameEn,
                                       type.to.lat,
                                       type.to.lon)

        return DriverPostEntity(placeFrom,
                               placeTo,
                               type.price,
                               type.departureDate,
                               type.timeFirstPart,
                               type.timeSecondPart,
                               type.timeThirdPart,
                               type.timeFourthPart,
                               type.carId,
                               type.remark,
                               type.seat,
                               type.postType)
    }


}