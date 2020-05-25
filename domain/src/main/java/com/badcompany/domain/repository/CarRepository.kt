package com.badcompany.domain.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.core.ErrorWrapper
import com.badcompany.domain.domainmodel.*
import java.io.File

interface CarRepository {

    suspend fun uploadPhoto(file: File): ResultWrapper<PhotoBody>
    suspend  fun getCarModels(token : String): ResultWrapper<List<CarModelBody>>
    suspend  fun getCarColors(token : String): ResultWrapper<List<CarColorBody>>



}