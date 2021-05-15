package com.novatec.data.mapper

import com.novatec.data.model.AuthEntity
import com.novatec.data.model.ImageEntity
import com.novatec.domain.domainmodel.AuthBody
import com.novatec.domain.domainmodel.Image
import com.novatec.domain.domainmodel.User
import javax.inject.Inject


/**
 * Map a [AuthEntity] to and from a [User] instance when data is moving between
 * this later and the Domain layer
 */
open class AuthMapper @Inject constructor() : Mapper<AuthEntity, AuthBody> {

    /**
     * Map a [AuthEntity] instance to a [User] instance
     */
    override fun mapFromEntity(type: AuthEntity): AuthBody {
        var image: Image? = null
        type.image?.let { image = Image(it.id, it.link) }
        return AuthBody(type.id,
                        type.phoneNum,
                        type.name,
                        type.surname,
                        type.jwt,
                        type.rating,
                        type.defCarId,
                        type.role,
                        image)
    }

    /**
     * Map a [User] instance to a [AuthEntity] instance
     */
    override fun mapToEntity(type: AuthBody): AuthEntity {
        var image: ImageEntity? = null
        type.image?.let { image = ImageEntity(it.id, it.link) }
        return AuthEntity(type.id,
                          type.phoneNum,
                          type.name,
                          type.jwt,
                          type.surname,
                          type.rating,
                          type.defCarId,
                          type.role,
                          image)
    }


}