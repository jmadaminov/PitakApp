package com.novatec.remote.mapper

import com.novatec.data.model.CarColorEntity
import com.novatec.data.model.UserEntity
import com.novatec.remote.model.CarColorModel
import com.novatec.remote.model.UserModel
import javax.inject.Inject

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class CarColorMapper : EntityMapper<CarColorModel, CarColorEntity> {

    override fun mapToEntity(type: CarColorModel): CarColorEntity {
        return CarColorEntity(type.id, type.hex, type.name)

    }

    override fun mapFromEntity(type: CarColorEntity): CarColorModel {
        return CarColorModel(type.id, type.hex, type.name)
    }


}