package com.badcompany.data.source

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.PhotoEntity
import com.badcompany.data.repository.*
import java.io.File
import javax.inject.Inject

/**
 * Implementation of the [UserDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class FileUploadRemoteDataStore @Inject constructor(private val fileUploadRemote: FileUploadRemote) :
    FileUploadDataStore {

    override suspend fun uploadPhoto(file: File): ResultWrapper<PhotoEntity> {
        return fileUploadRemote.uploadPhoto(file)
    }



}