package com.badcompany.data

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.mapper.PhotoMapper
import com.badcompany.data.source.FileUploadDataStoreFactory
import com.badcompany.domain.domainmodel.PhotoBody
import com.badcompany.domain.repository.FileUploadRepository
import com.badcompany.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

/**
 * Provides an implementation of the [UserRepository] interface for communicating to and from
 * data sources
 */
class FileUploadRepositoryImpl @Inject constructor(private val factory: FileUploadDataStoreFactory,
                                                   private val photoMapper: PhotoMapper
) : FileUploadRepository {

    override suspend fun uploadPhoto(file: File): ResultWrapper<PhotoBody> {
        val response = factory.retrieveDataStore(false)
            .uploadPhoto(file)
        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> ResultWrapper.Success(photoMapper.mapFromEntity(response.value))
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }




}