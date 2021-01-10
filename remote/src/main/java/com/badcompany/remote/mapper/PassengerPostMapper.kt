package com.badcompany.remote.mapper

import com.badcompany.data.mapper.Mapper
import com.badcompany.data.model.*
import com.badcompany.remote.model.*
import javax.inject.Inject


/**
 * Map a [DriverPostEntity] to and from a [DriverPostModel] instance when data is moving between
 * this later and the Domain layer
 */
open class PassengerPostMapper @Inject constructor() :
    Mapper<PassengerPostEntity, PassengerPostModel> {

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

        val profileImage = type.profileDTO.image?.let {
            Image(it.id, it.link)
        }

        val profileDTO = ProfileDTO(type.profileDTO.phoneNum,
                                    type.profileDTO.name,
                                    type.profileDTO.surname,
                                    type.profileDTO.id,
                                    profileImage)

        return PassengerPostModel(type.id,
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
                                  profileDTO,
                                  type.remark,
                                  type.postStatus,
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

        val profileImage = type.profileDTO.image?.let {
            ImageEntity(it.id, it.link)
        }

        val profileDTO = ProfileEntity(type.profileDTO.phoneNum,
                                       type.profileDTO.name,
                                       type.profileDTO.surname,
                                       type.profileDTO.id,
                                       profileImage)

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
                                   profileDTO,
                                   type.remark,
                                   type.postStatus,
                                   type.seat,
                                   type.postType)
    }

}