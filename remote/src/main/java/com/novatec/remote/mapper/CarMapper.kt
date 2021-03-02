package com.novatec.remote.mapper

import com.novatec.data.model.CarDetailsEntity
import com.novatec.data.model.CarEntity
import com.novatec.data.model.PhotoEntity
import com.novatec.data.model.UserEntity
import com.novatec.remote.model.CarDetails
import com.novatec.remote.model.CarModel
import com.novatec.remote.model.PhotoUploadModel
import com.novatec.remote.model.UserModel
import javax.inject.Inject

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class CarMapper @Inject constructor() : EntityMapper<CarModel, CarEntity> {

    override fun mapToEntity(type: CarModel): CarEntity {

        val images = arrayListOf<PhotoEntity>()
        type.imageList?.forEach { images.add(PhotoEntity(it.id)) }

        return CarEntity(type.id,
                         type.modelId,
                         type.imageId,
                         type.fuelType,
                         type.colorId,
                         type.carNumber,
                         type.carYear,
                         type.airConditioner,
                         type.def,
                         images)

    }

    override fun mapFromEntity(type: CarEntity): CarModel {
        val images = arrayListOf<PhotoUploadModel>()
        type.imageList?.forEach { images.add(PhotoUploadModel(it.id)) }
        return CarModel(type.id,
                        type.modelId,
                        type.imageId,
                        type.fuelType,
                        type.colorId,
                        type.carNumber,
                        type.carYear,
                        type.airConditioner,
                        type.def,
                        images)
    }


}