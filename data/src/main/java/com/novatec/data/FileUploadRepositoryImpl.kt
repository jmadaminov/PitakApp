package com.novatec.data

import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.data.mapper.PhotoMapper
import com.novatec.data.source.FileUploadDataStoreFactory
import com.novatec.domain.domainmodel.PhotoBody
import com.novatec.domain.repository.FileUploadRepository
import com.novatec.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [UserRepository] interface for communicating to and from
 * data sources
 */
class FileUploadRepositoryImpl @Inject constructor(private val factory: FileUploadDataStoreFactory,
                                                   private val photoMapper: PhotoMapper
) : FileUploadRepository {

    override suspend fun uploadPhoto(bytes: ByteArray): ResultWrapper<PhotoBody> {
        return when (val response = factory.retrieveDataStore(false).uploadPhoto(bytes)) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> ResultWrapper.Success(photoMapper.mapFromEntity(response.value))
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }


}