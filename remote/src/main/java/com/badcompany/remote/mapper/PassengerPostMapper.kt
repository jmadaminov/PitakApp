package com.badcompany.remote.mapper

import com.badcompany.data.mapper.Mapper
import com.badcompany.data.model.*
import com.badcompany.remote.model.*
import javax.inject.Inject


/**
 * Map a [DriverPostEntity] to and from a [DriverPostModel] instance when data is moving between
 * this later and the Domain layer
 */
open class PassengerPostMapper @Inject constructor() : Mapper<PassengerPostEntity, PassengerPostModel> {

    /**
     * Map a [PassengerPostEntity] instance to a [PassengerPostModel] instance
     */
    override fun mapFromEntity(type: PassengerPostEntity): PassengerPostModel {

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

        return PassengerPostModel(type.id,
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
     * Map a [PassengerPostModel] instance to a [PassengerPostEntity] instance
     */
    override fun mapToEntity(type: PassengerPostModel): PassengerPostEntity {

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


        return PassengerPostEntity(type.id,
                               placeFrom,
                               placeTo,
                               type.price,
                               type.departureDate,
                               type.createdDate,
                               type.updatedDate,
                               type.finishedDate,
                               type.timeFirstPart,
                               type.timeSecondPart,
                               type.timeThirdPart,
                               type.timeFourthPart,
                               type.airConditioner,
                               type.remark,
                               type.seat,
                               type.postType)
    }

}