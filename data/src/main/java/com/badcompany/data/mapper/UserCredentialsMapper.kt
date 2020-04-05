package com.badcompany.data.mapper

import com.badcompany.data.model.UserCredentialsEntity
import com.badcompany.domain.domainmodel.User
import com.badcompany.data.model.UserEntity
import com.badcompany.domain.domainmodel.UserCredentials
import javax.inject.Inject


/**
 * Map a [UserEntity] to and from a [User] instance when data is moving between
 * this later and the Domain layer
 */
open class UserCredentialsMapper @Inject constructor(): Mapper<UserCredentialsEntity, UserCredentials> {

    /**
     * Map a [UserEntity] instance to a [User] instance
     */
    override fun mapFromEntity(type: UserCredentialsEntity): UserCredentials {
        return UserCredentials(type.phone, type.password)
    }

    /**
     * Map a [User] instance to a [UserEntity] instance
     */
    override fun mapToEntity(type: UserCredentials): UserCredentialsEntity {
        return UserCredentialsEntity(type.phone, type.password)
    }


}