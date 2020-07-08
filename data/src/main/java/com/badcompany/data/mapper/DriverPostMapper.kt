package com.badcompany.data.mapper

import com.badcompany.data.model.*
import com.badcompany.domain.domainmodel.*
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
                              type.from.lat,
                              type.from.lon,
                              type.from.regionName,
                              type.from.districtName)

        val placeTo = Place(type.from.districtId,
                            type.from.regionId,
                            type.from.lat,
                            type.from.lon,
                            type.from.regionName,
                            type.from.districtName)


        val car = if (type.car == null) null else CarInPost(type.car.id,
                                                            type.car.modelId,
                                                            Image(type.car.image!!.id,
                                                                  type.car.image!!.link),
                                                            CarModelBody(type.car.carModel!!.id,
                                                                         type.car.carModel!!.name),
                                                            type.car.fuelType,
                                                            type.car.colorId,
                                                            type.car.carNumber,
                                                            type.car.carYear,
                                                            type.car.airConditioner)

        return DriverPost(type.id,
                          placeFrom,
                          placeTo,
                          type.price,
                          type.departureDate,
                          type.finishedDate,
                          type.timeFirstPart,
                          type.timeSecondPart,
                          type.timeThirdPart,
                          type.timeFourthPart,
                          type.carId,
                          car,
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
                                    type.from.lat,
                                    type.from.lon,
                                    type.from.regionName,
                                    type.from.districtName)

        val placeTo = PlaceEntity(type.from.districtId,
                                  type.from.regionId,
                                  type.from.lat,
                                  type.from.lon,
                                  type.from.regionName,
                                  type.from.districtName)

        val car = if (type.car == null) null else CarInPostEntity(type.car!!.id,
                                                                  type.car!!.modelId,
                                                                  ImageEntity(type.car!!.image!!.id,
                                                                                  type.car!!.image!!.link),
                                                                  CarModelEntity(type.car!!.carModel!!.id,
                                                                                     type.car!!.carModel!!.name),
                                                                  type.car!!.fuelType,
                                                                  type.car!!.colorId,
                                                                  type.car!!.carNumber,
                                                                  type.car!!.carYear,
                                                                  type.car!!.airConditioner)

        return DriverPostEntity(type.id,
                                placeFrom,
                                placeTo,
                                type.price,
                                type.departureDate,
                                type.departureDate,
                                type.timeFirstPart,
                                type.timeSecondPart,
                                type.timeThirdPart,
                                type.timeFourthPart,
                                type.carId,
                                car,
                                type.remark,
                                type.seat,
                                type.postType)
    }

}