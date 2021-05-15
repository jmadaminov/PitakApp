package com.novatec.remote.mapper

import com.novatec.data.model.AuthEntity
import com.novatec.data.model.ImageEntity
import com.novatec.remote.model.ImageBody
import com.novatec.remote.model.UserInfoModel
import javax.inject.Inject

/**
 * Map a [UserInfoModel] to and from a [AuthEntity] instance when data is moving between
 * this later and the Data layer
 */
open class AuthMapper : EntityMapper<UserInfoModel, AuthEntity> {

    /**
     * Map an instance of a [UserInfoModel] to a [AuthEntity] model
     */
    override fun mapToEntity(type: UserInfoModel): AuthEntity {
        var image :ImageEntity? = null

        type.image?.let {
            image = ImageEntity(it.id, it.link)
        }

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

    override fun mapFromEntity(type: AuthEntity): UserInfoModel {
        var image :ImageBody? = null

        type.image?.let {
            image = ImageBody(it.id, it.link)
        }
        return UserInfoModel(type.id,
                             type.phoneNum,
                             type.name,
                             type.surname,
                             type.jwt,
                             type.rating,
                             type.defCarId,
                             type.role,
                             image)
    }


}