package com.novatec.data.repository

import com.novatec.core.ResultWrapper
import com.novatec.data.model.CarColorEntity
import com.novatec.data.model.CarModelEntity
import com.novatec.data.model.PhotoEntity
import java.io.File


/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface FileUploadDataStore {

    suspend fun uploadPhoto(bytes: ByteArray): ResultWrapper<PhotoEntity>

}