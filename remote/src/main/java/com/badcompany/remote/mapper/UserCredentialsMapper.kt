package com.badcompany.remote.mapper

import com.badcompany.data.model.UserCredentialsEntity
import com.badcompany.data.model.UserEntity
import com.badcompany.remote.model.UserCredentialsModel
import com.badcompany.remote.model.UserModel
import javax.inject.Inject

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class UserCredentialsMapper @Inject constructor() :
    EntityMapper<UserCredentialsModel, UserCredentialsEntity> {

    /**
     * Map an instance of a [UserModel] to a [UserEntity] model
     */
    override fun mapToEntity(type: UserCredentialsModel): UserCredentialsEntity {
        return UserCredentialsEntity(type.phoneNum, type.password, type.udId)
    }

    override fun mapFromEntity(type: UserCredentialsEntity): UserCredentialsModel {
        return UserCredentialsModel(type.phoneNum, type.password, type.deviceId)
    }
}