package com.badcompany.remote

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.model.PlaceEntity
import com.badcompany.data.repository.PlaceRemote
import com.badcompany.remote.mapper.PlaceMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class PlaceRemoteImpl @Inject constructor(private val apiService: ApiService,
                                          private val placeMapper: PlaceMapper) : PlaceRemote {

    override suspend fun getPlacesAutocomplete(token: String,
                                               queryString: String): ResultWrapper<List<PlaceEntity>> {
        return try {
            val response = apiService.getPlacesAutocomplete(token, queryString)
            if (response.code == 1) {
                val places = arrayListOf<PlaceEntity>()
                response.data!!.forEach {
                    places.add(placeMapper.mapToEntity(it))
                }
                ResultWrapper.Success(places)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }


}