package com.novatec.data.mapper

import com.novatec.data.model.*
import com.novatec.domain.domainmodel.*
import javax.inject.Inject


/**
 * Map a [UserEntity] to and from a [User] instance when data is moving between
 * this later and the Domain layer
 */
open class CarDetailsMapper @Inject constructor() : Mapper<CarDetailsEntity, CarDetails> {

    /**
     * Map a [UserEntity] instance to a [User] instance
     */
    override fun mapFromEntity(type: CarDetailsEntity): CarDetails {

        val images = arrayListOf<Image>()
        type.imageList?.forEach { images.add(Image(it.id, it.link)) }

        return CarDetails(type.id,
                          IdName(type.carModel!!.id, type.carModel!!.name),
                          Image(type.image!!.id, type.image!!.link),
                          type.fuelType,
                          CarColorBody(type.carColor!!.id,
                                       type.carColor!!.hex,
                                       type.carColor!!.name),
                          type.carNumber,
                          type.carYear,
                          type.airConditioner,
                          type.def,
                          images)
    }

    /**
     * Map a [User] instance to a [UserEntity] instance
     */
    override fun mapToEntity(type: CarDetails): CarDetailsEntity {
        val images = arrayListOf<ImageEntity>()
        type.imageList?.forEach { images.add(ImageEntity(it.id, it.link)) }

        return CarDetailsEntity(type.id,
                                IdNameEntity(type.carModel!!.id, type.carModel!!.name),
                                ImageEntity(type.image!!.id, type.image!!.link),
                                type.fuelType,
                                CarColorEntity(type.carColor!!.id!!,
                                               type.carColor!!.hex!!,
                                               type.carColor!!.name),
                                type.carNumber,
                                type.carYear,
                                type.airConditioner,
                                type.def,
                                images)
    }


}
