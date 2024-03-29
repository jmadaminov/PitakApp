package com.novatec.data.mapper

import com.novatec.data.model.*
import com.novatec.domain.domainmodel.*
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
                              type.from.districtName,
                              type.from.name)

        val placeTo = Place(type.to.districtId,
                            type.to.regionId,
                            type.to.lat,
                            type.to.lon,
                            type.to.regionName,
                            type.to.districtName,
                            type.to.name)


        val car = if (type.car == null) null else CarInPost(type.car.id,
                                                            type.car.modelId,
                                                            Image(type.car.image!!.id,
                                                                  type.car.image!!.link),
                                                            CarModelBody(type.car.carModel!!.id,
                                                                         type.car.carModel!!.name),
                                                            type.car.fuelType,
                                                            CarColorBody(type.car.color!!.id,
                                                                         type.car.color!!.hex,
                                                                         type.car.color!!.name!!),
                                                            type.car.carNumber,
                                                            type.car.carYear,
                                                            type.car.airConditioner,
                                                            type.car.def)

        val passengerList = arrayListOf<Passenger>()
        type.passengerList?.forEach {
            val offerEntity =
                ArrangedOffer(it.offer?.message,
                              it.offer?.priceInt,
                              it.offer?.seat,
                              it.offer?.history)
            val profile = Profile(it.profile!!.phoneNum,
                                  it.profile!!.name,
                                  it.profile!!.surname,
                                  it.profile!!.id,
                                  Image(it.profile!!.image?.id,
                                        it.profile!!.image?.link))

            passengerList.add(Passenger(it.id,
                                        profile,
                                        it.submitDate,
                                        offerEntity))
        }

        val parcelList = type.parcelList?.map {
            val image = Image(it.profile.image?.id, it.profile.image?.link)
            val profile = Profile(it.profile.phoneNum,
                                  it.profile.name,
                                  it.profile.surname,
                                  it.profile.id,
                                  image)
            val parcelImage = Image(it.offer.image?.id, it.offer.image?.link)
            val offer = Offer(it.offer.id,
                              it.offer.postId,
                              it.offer.offerType,
                              it.offer.profileId,
                              profile,
                              it.offer.status,
                              it.offer.submitDate,
                              it.offer.message,
                              parcelImage,
                              it.offer.price,
                              null)
            Parcel(it.id, profile, it.submitDate, offer, it.status)
        }
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
                          type.passengerOfferCount,
                          type.passengerCount,
                          type.availableSeats,
                          type.postStatus,
                          type.pkg,
                          type.parcelOfferCount,
                          passengerList,
                          parcelList,
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
                                    type.from.districtName,
                                    type.from.name)

        val placeTo = PlaceEntity(type.to.districtId,
                                  type.to.regionId,
                                  type.to.lat,
                                  type.to.lon,
                                  type.to.regionName,
                                  type.to.districtName,
                                  type.to.name)

        val car = if (type.car == null) null else CarInPostEntity(type.car!!.id,
                                                                  type.car!!.modelId,
                                                                  ImageEntity(type.car!!.image!!.id,
                                                                              type.car!!.image!!.link),
                                                                  CarModelEntity(type.car!!.carModel!!.id,
                                                                                 type.car!!.carModel!!.name),
                                                                  type.car!!.fuelType,
                                                                  CarColorEntity(type.car!!.color!!.id!!,
                                                                                 type.car!!.color!!.hex,
                                                                                 type.car!!.color!!.name),
                                                                  type.car!!.carNumber,
                                                                  type.car!!.carYear,
                                                                  type.car!!.airConditioner,
                                                                  type.car!!.def)

        val passengerList = type.passengerList?.map {

            val offer = ArrangedOfferEntity(it.offer?.message,
                                            it.offer?.priceInt,
                                            it.offer?.seat,
                                            it.offer?.history)

            val profile = ProfileEntity(it.profile!!.phoneNum,
                                        it.profile!!.name,
                                        it.profile!!.surname,
                                        it.profile!!.id,
                                        ImageEntity(it.profile!!.image?.id,
                                                    it.profile!!.image?.link))
            PassengerEntity(it.id, profile, it.submitDate, offer)
        }


        val parcelList = type.parcelList?.map {
            val image = ImageEntity(it.profile.image?.id, it.profile.image?.link)
            val profile = ProfileEntity(it.profile.phoneNum,
                                        it.profile.name,
                                        it.profile.surname,
                                        it.profile.id,
                                        image)
            val parcelImage = ImageEntity(it.offer.image?.id, it.offer.image?.link)
            val offer = OfferEntity(it.offer.id,
                                    it.offer.postId,
                                    it.offer.offerType,
                                    it.offer.profileId,
                                    profile,
                                    it.offer.status,
                                    it.offer.submitDate,
                                    it.offer.message,
                                    parcelImage,
                                    it.offer.price,
                                    null)
            ParcelEntity(it.id, profile, it.submitDate, offer, it.status)
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
                                type.passengerOfferCount,
                                type.passengerCount,
                                type.availableSeats,
                                type.postStatus,
                                type.pkg,
                                type.parcelOfferCount,
                                passengerList,
                                parcelList,
                                type.postType)
    }

}