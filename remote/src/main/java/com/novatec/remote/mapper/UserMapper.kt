package com.novatec.remote.mapper

import com.novatec.data.model.UserCredentialsEntity
import com.novatec.data.model.UserEntity
import com.novatec.remote.model.UserCredentialsModel
import com.novatec.remote.model.UserInfoModel
import com.novatec.remote.model.UserModel
import javax.inject.Inject

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class UserMapper @Inject constructor(): EntityMapper<UserModel, UserEntity> {

    /**
     * Map an instance of a [UserModel] to a [UserEntity] model
     */
    override fun mapToEntity(type: UserModel): UserEntity {
        return UserEntity(type.phoneNum, type.name, type.surname, /*type.role,*/ type.udId)
    }

    override fun mapFromEntity(type: UserEntity): UserModel {
        return UserModel(type.phoneNum, type.name, type.surname, /*type.role, */type.deviceId)
    }


}