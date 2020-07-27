package com.badcompany.remote.mapper

import com.badcompany.data.mapper.Mapper
import com.badcompany.data.model.*
import com.badcompany.remote.model.*
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
                                   type.from.lat,
                                   type.from.lon,
                                   type.from.regionName,
                                   type.from.name)

        val placeTo = PlaceModel(type.to.districtId,
                                 type.to.regionId,
                                 type.to.lat,
                                 type.to.lon,
                                 type.to.regionName,
                                 type.to.name)


        val car =  if (type.car == null) null else CarInPostModel(type.car!!.id,
                                 type.car!!.modelId,
                                 ImageModel(type.car!!.image!!.id, type.car!!.image!!.link),
                                 CarModelModel(type.car!!.carModel!!.id, type.car!!.carModel!!.name),
                                 type.car!!.fuelType,
                                 type.car!!.colorId,
                                 type.car!!.carNumber,
                                 type.car!!.carYear,
                                 type.car!!.airConditioner)

        return DriverPostModel(type.id,
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
                               type.availableSeats,
                               type.postType)
    }

    /**
     * Map a [DriverPostModel] instance to a [DriverPostEntity] instance
     */
    override fun mapToEntity(type: DriverPostModel): DriverPostEntity {

        val placeFrom = PlaceEntity(type.from.districtId,
                                    type.from.regionId,
                                    type.from.lat,
                                    type.from.lon,
                                    type.from.regionName,
                                    type.from.name)

        val placeTo = PlaceEntity(type.to.districtId,
                                  type.to.regionId,
                                  type.to.lat,
                                  type.to.lon,
                                  type.to.regionName,
                                  type.to.name)

        val car = if (type.car == null) null else  CarInPostEntity(type.car.id,
                                  type.car.modelId,
                                  ImageEntity(type.car.image!!.id, type.car.image!!.link),
                                  CarModelEntity(type.car.carModel!!.id, type.car.carModel!!.name),
                                  type.car.fuelType,
                                  type.car.colorId,
                                  type.car.carNumber,
                                  type.car.carYear,
                                  type.car.airConditioner)

        return DriverPostEntity(type.id,
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
                                type.availableSeats,
                                type.postType)
    }

}