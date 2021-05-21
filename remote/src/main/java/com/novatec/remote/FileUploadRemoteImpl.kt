package com.novatec.remote

import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.data.model.PhotoEntity
import com.novatec.data.repository.FileUploadRemote
import com.novatec.remote.mapper.PhotoMapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class FileUploadRemoteImpl @Inject constructor(private val apiService: ApiService,
                                               private val photoMapper: PhotoMapper) :
    FileUploadRemote {


    override suspend fun uploadPhoto(bytes: ByteArray): ResultWrapper<PhotoEntity> {
        return try {
            val requestFile = bytes.toRequestBody("image/jpg".toMediaType())
//            val requestFile = file.asRequestBody("image/jpg".toMediaType())
            val body = MultipartBody.Part.createFormData("file", "", requestFile)
            val response = apiService.uploadPhoto(body)
            if (response.code == 1) ResultWrapper.Success(photoMapper.mapToEntity(response.data!!))
            else ErrorWrapper.RespError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

}