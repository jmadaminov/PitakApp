package com.badcompany.data.repository

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.*
import com.badcompany.domain.domainmodel.AuthBody
import java.io.File


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface CarRemote {

    suspend  fun uploadPhoto(file: File): ResultWrapper<PhotoEntity>
    suspend  fun getCarModels(token : String): ResultWrapper<List<CarModelEntity>>
    suspend  fun getCarColors(token : String): ResultWrapper<List<CarColorEntity>>

}