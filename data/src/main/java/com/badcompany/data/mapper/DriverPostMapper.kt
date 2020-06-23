package com.badcompany.data.mapper

import com.badcompany.data.model.DriverPostEntity
import com.badcompany.data.model.PlaceEntity
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.domainmodel.Place
import javax.inject.Inject


/**
 * Map a [DriverPostEntity] to and from a [DriverPost] instance when data is moving between
 * this later and the Domain layer
 */
open class DriverPostMapper @Inject constructor() : Mapper<DriverPostEntity, DriverPost> {

    /**
     * Map a [DriverPostEntity] instance to a [DriverPost] instance
     */
    override fun mapFromEntity(type: DriverPostEntity): DriverPost {
        val placeFrom = Place(type.from.districtId,
                              type.from.regionId,
                              type.from.nameRu,
                              type.from.nameUz,
                              type.from.nameEn,
                              type.from.lat,
                              type.from.lon)

        val placeTo = Place(type.to.districtId,
                            type.to.regionId,
                            type.to.nameRu,
                            type.to.nameUz,
                            type.to.nameEn,
                            type.to.lat,
                            type.to.lon)

        return DriverPost(placeFrom,
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
     * Map a [DriverPost] instance to a [DriverPostEntity] instance
     */
    override fun mapToEntity(type: DriverPost): DriverPostEntity {
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