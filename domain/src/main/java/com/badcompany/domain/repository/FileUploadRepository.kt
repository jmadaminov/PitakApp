package com.badcompany.domain.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.CarColorBody
import com.badcompany.domain.domainmodel.CarModelBody
import com.badcompany.domain.domainmodel.PhotoBody
import java.io.File

interface FileUploadRepository {

    suspend fun uploadPhoto(file: File): ResultWrapper<PhotoBody>

}