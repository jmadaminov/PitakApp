package com.novatec.data.source

import com.novatec.core.ResultWrapper
import com.novatec.data.model.PhotoEntity
import com.novatec.data.repository.*
import java.io.File
import javax.inject.Inject

/**
 * Implementation of the [UserDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class FileUploadRemoteDataStore @Inject constructor(private val fileUploadRemote: FileUploadRemote) :
    FileUploadDataStore {

    override suspend fun uploadPhoto(bytes: ByteArray): ResultWrapper<PhotoEntity> {
        return fileUploadRemote.uploadPhoto(bytes)
    }



}