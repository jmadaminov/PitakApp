package com.badcompany.data.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.CarColorEntity
import com.badcompany.data.model.CarEntity
import com.badcompany.data.model.CarModelEntity
import com.badcompany.data.model.PhotoEntity
import java.io.File


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface FileUploadRemote {
    suspend fun uploadPhoto(file: File): ResultWrapper<PhotoEntity>

}