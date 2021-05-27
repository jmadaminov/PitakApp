package com.novatec.remote

import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.data.model.PlaceEntity
import com.novatec.data.repository.PlaceRemote
import com.novatec.remote.mapper.PlaceMapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class PlaceRemoteImpl @Inject constructor(
                                          private val authorizedApiService: AuthorizedApiService,
                                          private val placeMapper: PlaceMapper) : PlaceRemote {

    override suspend fun getPlacesAutocomplete(queryString: String): ResultWrapper<List<PlaceEntity>> {
        return try {
            val response = authorizedApiService.getPlacesFeed(  queryString)
            if (response.code == 1) {
                val places = arrayListOf<PlaceEntity>()
                response.data!!.forEach {
                    places.add(placeMapper.mapToEntity(it))
                }
                ResultWrapper.Success(places)
            } else ErrorWrapper.RespError(response.code, response.message?:"")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }


}