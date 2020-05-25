package com.badcompany.remote.mapper

import com.badcompany.data.model.CarModelEntity
import com.badcompany.data.model.UserEntity
import com.badcompany.remote.model.CarModelModel
import com.badcompany.remote.model.UserModel
import javax.inject.Inject

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class CarModelMapper @Inject constructor() : EntityMapper<CarModelModel, CarModelEntity> {

    override fun mapToEntity(type: CarModelModel): CarModelEntity {
        return CarModelEntity(type.id, type.name)

    }

    override fun mapFromEntity(type: CarModelEntity): CarModelModel {
        return CarModelModel(type.id, type.name)
    }


}