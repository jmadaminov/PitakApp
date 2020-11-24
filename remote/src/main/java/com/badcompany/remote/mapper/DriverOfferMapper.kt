package com.badcompany.remote.mapper

import com.badcompany.data.mapper.Mapper
import com.badcompany.data.model.DriverOfferEntity
import com.badcompany.remote.model.DriverOfferBody
import javax.inject.Inject


/**
 * Map a [DriverOfferEntity] to and from a [DriverOfferBody] instance when data is moving between
 * this later and the Domain layer
 */
open class DriverOfferMapper @Inject constructor() : Mapper<DriverOfferEntity, DriverOfferBody> {

    /**
     * Map a [DriverOfferEntity] instance to a [DriverOfferBody] instance
     */
    override fun mapFromEntity(type: DriverOfferEntity): DriverOfferBody {
        return DriverOfferBody(type.postId, type.price, type.message, type.repliedPostId)
    }

    /**
     * Map a [DriverOfferBody] instance to a [DriverOfferEntity] instance
     */
    override fun mapToEntity(type: DriverOfferBody): DriverOfferEntity {
        return DriverOfferEntity(type.postId, type.price, type.message, type.repliedPostId)
    }

}