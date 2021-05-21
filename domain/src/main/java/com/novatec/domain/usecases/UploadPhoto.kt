package com.novatec.domain.usecases

import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.PhotoBody
import com.novatec.domain.repository.CarRepository
import com.novatec.domain.repository.FileUploadRepository
import java.io.File


/** User Login Use Case
 *
 */
class UploadPhoto(val repository: FileUploadRepository) :
    UseCaseWithParams<ByteArray, ResultWrapper<PhotoBody>>() {

    override suspend fun buildUseCase(params:ByteArray): ResultWrapper<PhotoBody> {
        return repository.uploadPhoto(params)
    }
}