package com.badcompany.remote.mapper

import com.badcompany.data.model.*
import com.badcompany.remote.model.*
import javax.inject.Inject

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class CarDetailsMapper @Inject constructor() : EntityMapper<CarDetails, CarDetailsEntity> {

    override fun mapToEntity(type: CarDetails): CarDetailsEntity {
        val images = arrayListOf<ImageEntity>()
        type.imageList?.forEach { images.add(ImageEntity(it.id, it.link)) }

        return CarDetailsEntity(type.id,
                                IdNameEntity(type.carModel!!.id, type.carModel!!.name),
                                ImageEntity(type.image!!.id, type.image!!.link),
                                type.fuelType,
                                CarColorEntity(type.carColor!!.id,
                                               type.carColor!!.hex,
                                               type.carColor!!.name),
                                type.carNumber,
                                type.carYear,
                                type.airConditioner,
                                type.def,
                                images)

    }

    override fun mapFromEntity(type: CarDetailsEntity): CarDetails {
        val images = arrayListOf<ImageBody>()
        type.imageList?.forEach { images.add(ImageBody(it.id, it.link)) }
        return CarDetails(type.id,
                          IdNameBody(type.carModel!!.id, type.carModel!!.name),
                          ImageBody(type.image!!.id, type.image!!.link),
                          type.fuelType,
                          CarColorModel(type.carColor!!.id,
                                        type.carColor!!.hex,
                                        type.carColor!!.name),
                          type.carNumber,
                          type.carYear,
                          type.airConditioner,
                          type.def,
                          images)
    }


}