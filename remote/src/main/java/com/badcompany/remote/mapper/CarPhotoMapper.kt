package com.badcompany.remote.mapper

import com.badcompany.data.model.PhotoEntity
import com.badcompany.data.model.UserEntity
import com.badcompany.remote.model.PhotoUploadModel
import com.badcompany.remote.model.UserModel
import javax.inject.Inject

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class CarPhotoMapper @Inject constructor() : EntityMapper<PhotoUploadModel, PhotoEntity> {

    /**
     * Map an instance of a [UserModel] to a [UserEntity] model
     */
    override fun mapToEntity(type: PhotoUploadModel): PhotoEntity {
        return PhotoEntity(type.id, type.name, type.type, type.size, type.link)
    }

    override fun mapFromEntity(type: PhotoEntity): PhotoUploadModel {
        return PhotoUploadModel(type.id, type.name, type.type, type.size, type.link)
    }


}