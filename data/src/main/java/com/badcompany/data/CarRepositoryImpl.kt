package com.badcompany.data

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.mapper.CarColorMapper
import com.badcompany.data.mapper.CarModelMapper
import com.badcompany.data.mapper.CarPhotoMapper
import com.badcompany.data.source.CarDataStoreFactory
import com.badcompany.domain.domainmodel.CarColorBody
import com.badcompany.domain.domainmodel.CarModelBody
import com.badcompany.domain.domainmodel.PhotoBody
import com.badcompany.domain.repository.CarRepository
import com.badcompany.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

/**
 * Provides an implementation of the [UserRepository] interface for communicating to and from
 * data sources
 */
class CarRepositoryImpl @Inject constructor(private val factory: CarDataStoreFactory,
                                            private val photoMapper: CarPhotoMapper,
                                            private val colorMapper: CarColorMapper,
                                            private val modelMapper: CarModelMapper
) : CarRepository {

    override suspend fun uploadPhoto(file: File): ResultWrapper<PhotoBody> {
        val response = factory.retrieveDataStore(false)
            .uploadPhoto(file)
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> ResultWrapper.Success(photoMapper.mapFromEntity(response.value))
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun getCarModels(token: String): ResultWrapper<List<CarModelBody>> {
        val response = factory.retrieveDataStore(false)
            .getCarModels(token)
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val newCarColors = ArrayList<CarModelBody>()
                response.value.forEach {
                    newCarColors.add(modelMapper.mapFromEntity(it))
                }
                ResultWrapper.Success(newCarColors)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun getCarColors(token: String): ResultWrapper<List<CarColorBody>> {
        val response = factory.retrieveDataStore(false)
            .getCarColors(token)
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val newCarColors = ArrayList<CarColorBody>()
                response.value.forEach {
                    newCarColors.add(colorMapper.mapFromEntity(it))
                }
                ResultWrapper.Success(newCarColors)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }


}