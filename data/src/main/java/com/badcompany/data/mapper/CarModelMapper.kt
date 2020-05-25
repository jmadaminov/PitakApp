package com.badcompany.data.mapper

import com.badcompany.data.model.CarModelEntity
import com.badcompany.data.model.UserEntity
import com.badcompany.domain.domainmodel.CarModelBody
import com.badcompany.domain.domainmodel.User
import javax.inject.Inject


/**
 * Map a [UserEntity] to and from a [User] instance when data is moving between
 * this later and the Domain layer
 */
open class CarModelMapper @Inject constructor() : Mapper<CarModelEntity, CarModelBody> {

    /**
     * Map a [UserEntity] instance to a [User] instance
     */
    override fun mapFromEntity(type: CarModelEntity): CarModelBody {
        return CarModelBody(type.id, type.name)
    }

    /**
     * Map a [User] instance to a [UserEntity] instance
     */
    override fun mapToEntity(type: CarModelBody): CarModelEntity {
        return CarModelEntity(type.id, type.name)
    }


}