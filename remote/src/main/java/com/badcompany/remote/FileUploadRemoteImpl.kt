package com.badcompany.remote

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.PhotoEntity
import com.badcompany.data.repository.FileUploadRemote
import com.badcompany.remote.mapper.PhotoMapper
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class FileUploadRemoteImpl @Inject constructor(private val apiService: ApiService,
                                               private val photoMapper: PhotoMapper) :
    FileUploadRemote {


    override suspend fun uploadPhoto(file: File): ResultWrapper<PhotoEntity> {
        return try {
            val requestFile = file.asRequestBody("image/jpg".toMediaType())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val response = apiService.uploadPhoto(body)
            if (response.code == 1) ResultWrapper.Success(photoMapper.mapToEntity(response.data!!))
            else ErrorWrapper.RespError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

}