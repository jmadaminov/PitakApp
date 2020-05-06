package com.badcompany.data.mapper

import com.badcompany.data.model.AuthEntity
import com.badcompany.domain.domainmodel.AuthBody
import com.badcompany.domain.domainmodel.User
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
        return AuthBody(type.phoneNum, type.name, type.jwt, type.surname,  type.role)
    }

    /**
     * Map a [User] instance to a [AuthEntity] instance
     */
    override fun mapToEntity(type: AuthBody): AuthEntity {
        return AuthEntity(type.phoneNum, type.name,  type.jwt,type.surname,  type.role)
    }


}