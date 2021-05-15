package com.novatec.data.mapper

import com.novatec.data.model.*
import com.novatec.domain.domainmodel.*
import javax.inject.Inject


/**
 * Map a [DriverPostEntity] to and from a [DriverPostModel] instance when data is moving between
 * this later and the Domain layer
 */
open class PassengerPostMapper @Inject constructor() :
    Mapper<PassengerPostEntity, PassengerPost> {

    /**
     * Map a [PassengerPostEntity] instance to a [PassengerPost] instance
     */
    override fun mapFromEntity(type: PassengerPostEntity): PassengerPost {

        val placeFrom = Place(type.from.districtId,
                                   type.from.regionId,
                                   type.from.lat,
                                   type.from.lon,
                                   type.from.regionName,
                                   type.from.name)

        val placeTo = Place(type.to.districtId,
                                 type.to.regionId,
                                 type.to.lat,
                                 type.to.lon,
                                 type.to.regionName,
                                 type.to.name)

        val profileImage = type.profile.image?.let {
            Image(it.id, it.link)
        }

        val profile = Profile(type.profile.phoneNum,
                                          type.profile.name,
                                          type.profile.surname,
                                          type.profile.id,
                                          profileImage)


        val myLastOffer = if (type.myLastOffer != null) UserOffer(type.myLastOffer.id,
                                                                        type.myLastOffer.id,
                                                                        type.myLastOffer.repliedPostId,
                                                                        type.myLastOffer.status,
                                                                        type.myLastOffer.message,
                                                                        type.myLastOffer.submitDate,
                                                                        type.myLastOffer.priceInt,
                                                                        type.myLastOffer.seat) else null


        var agreedOffer : AgreedOffer? = null
        type.agreedOffer?.let{
            agreedOffer = AgreedOffer( it.message, it.priceInt,it.seat )
        }


        return PassengerPost(type.id,
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
                             profile,
                             type.remark,
                             type.postStatus,
                             myLastOffer,
                             type.seat,
                             agreedOffer,
                             type.postType)
    }

    /**
     * Map a [PassengerPost] instance to a [PassengerPostEntity] instance
     */
    override fun mapToEntity(type: PassengerPost): PassengerPostEntity {

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

        val profileImage = type.profile.image?.let {
            ImageEntity(it.id, it.link)
        }

        val profileEntity = ProfileEntity(type.profile.phoneNum,
                                       type.profile.name,
                                       type.profile.surname,
                                       type.profile.id,
                                       profileImage)

        val myLastOffer = if (type.myLastOffer != null) UserOfferEntity(type.myLastOffer!!.id,
                                                                        type.myLastOffer!!.id,
                                                                        type.myLastOffer!!.repliedPostId,
                                                                        type.myLastOffer!!.status,
                                                                        type.myLastOffer!!.message,
                                                                        type.myLastOffer!!.submitDate,
                                                                        type.myLastOffer!!.priceInt,
                                                                        type.myLastOffer!!.seat) else null
        var agreedOffer : AgreedOfferEntity? = null
        type.agreedOffer?.let{
            agreedOffer = AgreedOfferEntity( it.message, it.priceInt,it.seat )
        }


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
                                   profileEntity,
                                   type.remark,
                                   type.postStatus,
                                   myLastOffer,
                                   agreedOffer,
                                   type.seat,
                                   type.postType)
    }

}