package com.badcompany.data.mapper

import com.badcompany.data.model.*
import com.badcompany.domain.domainmodel.DriverOffer
import com.badcompany.domain.domainmodel.DriverPost
import javax.inject.Inject


/**
 * Map a [DriverPostEntity] to and from a [DriverPost] instance when data is moving between
 * this later and the Domain layer
 */
open class DriverOfferMapper @Inject constructor() : Mapper<DriverOfferEntity, DriverOffer> {

    /**
     * Map a [DriverPostEntity] instance to a [DriverPost] instance
     */
    override fun mapFromEntity(type: DriverOfferEntity): DriverOffer {
        return DriverOffer(type.postId, type.price, type.message, type.repliedPostId)
    }

    /**
     * Map a [DriverPost] instance to a [DriverPostEntity] instance
     */
    override fun mapToEntity(type: DriverOffer): DriverOfferEntity {
        return DriverOfferEntity(type.postId, type.price, type.message, type.repliedPostId)
    }

}