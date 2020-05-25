package com.badcompany.remote

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.CarColorEntity
import com.badcompany.data.model.CarModelEntity
import com.badcompany.data.model.PhotoEntity
import com.badcompany.data.repository.CarRemote
import com.badcompany.remote.mapper.CarColorMapper
import com.badcompany.remote.mapper.CarModelMapper
import com.badcompany.remote.mapper.CarPhotoMapper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class CarRemoteImpl @Inject constructor(private val apiService: ApiService,
                                        private val photoMapper: CarPhotoMapper,
                                        private val carModelMapper: CarModelMapper,
                                        private val carColorMapper: CarColorMapper
) : CarRemote {


    override suspend fun uploadPhoto(file: File): ResultWrapper<PhotoEntity> {
        return try {
            val requestFile = RequestBody.create(MediaType.parse("image/jpg"), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val response = apiService.uploadPhoto(body)
            if (response.code == 1) ResultWrapper.Success(photoMapper.mapToEntity(response.data!!))
            else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getCarModels(token : String): ResultWrapper<List<CarModelEntity>> {
        return try {
            val response = apiService.getCarModels(token)
            if (response.code == 1) {
                val newCarModels = ArrayList<CarModelEntity>()
                response.data!!.forEach {
                    newCarModels.add(carModelMapper.mapToEntity(it))
                }
                ResultWrapper.Success(newCarModels)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getCarColors(token: String): ResultWrapper<List<CarColorEntity>> {
        return try {
            val response = apiService.getCarColors(token)
            if (response.code == 1) {
                val newCarColors = ArrayList<CarColorEntity>()
                response.data!!.forEach {
                    newCarColors.add(carColorMapper.mapToEntity(it))
                }
                ResultWrapper.Success(newCarColors)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }


}