package com.badcompany.data.source

import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.CarColorEntity
import com.badcompany.data.model.CarModelEntity
import com.badcompany.data.model.PhotoEntity
import com.badcompany.data.repository.CarDataStore
import com.badcompany.data.repository.CarRemote
import com.badcompany.data.repository.UserDataStore
import java.io.File
import javax.inject.Inject

/**
 * Implementation of the [UserDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class CarRemoteDataStore @Inject constructor(private val carRemote: CarRemote) :
    CarDataStore {

    override suspend fun uploadPhoto(file: File): ResultWrapper<PhotoEntity> {
        return carRemote.uploadPhoto(file)
    }

    override suspend fun getCarModels(token: String): ResultWrapper<List<CarModelEntity>> {
        return carRemote.getCarModels(token)

    }

    override suspend fun getCarColors(token: String): ResultWrapper<List<CarColorEntity>> {
        return carRemote.getCarColors(token)

    }

}