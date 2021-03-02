package com.novatec.remote.mapper

import com.novatec.data.mapper.Mapper
import com.novatec.data.model.*
import com.novatec.remote.model.*
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


        val car = if (type.car == null) null else CarInPostModel(type.car!!.id,
                                                                 type.car!!.modelId,
                                                                 ImageModel(type.car!!.image!!.id,
                                                                            type.car!!.image!!.link),
                                                                 CarModelModel(type.car!!.carModel!!.id,
                                                                               type.car!!.carModel!!.name),
                                                                 type.car!!.fuelType,
                                                                 CarColorModel(type.car!!.color!!.id,
                                                                               type.car!!.color!!.hex,
                                                                               type.car!!.color!!.name!!),
                                                                 type.car!!.carNumber,
                                                                 type.car!!.carYear,
                                                                 type.car!!.airConditioner,
                                                                 type.car!!.def)

        val passengerList = arrayListOf<PassengerModel>()
        type.passengerList?.forEach {

            val offerEntity =
                ArrangedOfferDTO(it.offer?.message,
                                 it.offer?.priceInt,
                                 it.offer?.seat,
                                 it.offer?.history)

            passengerList.add(PassengerModel(it.id,
                                             ProfileDTO(it.profile!!.phoneNum,
                                                        it.profile!!.name,
                                                        it.profile!!.surname,
                                                        it.profile!!.id,
                                                        Image(it.profile!!.image?.id,
                                                              it.profile!!.image?.link)),
                                             it.submitDate,
                                             offerEntity))
        }

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
                               type.offerCount,
                               type.availableSeats,
                               type.postStatus,
                               type.pkg,
                               passengerList,
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

        val car = if (type.car == null) null else CarInPostEntity(type.car.id,
                                                                  type.car.modelId,
                                                                  ImageEntity(type.car.image!!.id,
                                                                              type.car.image!!.link),
                                                                  CarModelEntity(type.car.carModel!!.id,
                                                                                 type.car.carModel!!.name),
                                                                  type.car.fuelType,
                                                                  CarColorEntity(type.car.carColor!!.id,
                                                                                 type.car.carColor!!.hex,
                                                                                 type.car.carColor!!.name!!),
                                                                  type.car.carNumber,
                                                                  type.car.carYear,
                                                                  type.car.airConditioner,
                                                                  type.car.def)

        val passengerList = arrayListOf<PassengerEntity>()
        type.passengerList?.forEach {
            val offer =
                ArrangedOfferEntity(it.offer?.message,
                                    it.offer?.priceInt,
                                    it.offer?.seat,
                                    it.offer?.history)
            passengerList.add(PassengerEntity(it.id, ProfileEntity(it.profile!!.phoneNum,
                                                                   it.profile!!.name,
                                                                   it.profile!!.surname,
                                                                   it.profile!!.id,
                                                                   ImageEntity(it.profile!!.image?.id,
                                                                               it.profile!!.image?.link)),
                                              it.submitDate, offer))
        }

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
                                type.offerCount,
                                type.availableSeats,
                                type.postStatus,
                                type.pkg,
                                passengerList,
                                type.postType)
    }

}