package com.novatec.data.repository

import com.novatec.core.ResultWrapper
import com.novatec.data.model.CarColorEntity
import com.novatec.data.model.CarEntity
import com.novatec.data.model.CarModelEntity
import com.novatec.data.model.PhotoEntity
import java.io.File


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface FileUploadRemote {
    suspend fun uploadPhoto(bytes: ByteArray): ResultWrapper<PhotoEntity>

}