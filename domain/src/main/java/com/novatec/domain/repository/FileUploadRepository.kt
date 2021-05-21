package com.novatec.domain.repository

import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.Car
import com.novatec.domain.domainmodel.CarColorBody
import com.novatec.domain.domainmodel.CarModelBody
import com.novatec.domain.domainmodel.PhotoBody
import java.io.File

interface FileUploadRepository {

    suspend fun uploadPhoto(bytes: ByteArray): ResultWrapper<PhotoBody>

}