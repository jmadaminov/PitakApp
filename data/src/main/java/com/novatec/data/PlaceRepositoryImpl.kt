package com.novatec.data

import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.data.mapper.PlaceMapper
import com.novatec.data.source.PlaceDataStoreFactory
import com.novatec.domain.domainmodel.Place
import com.novatec.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [PlaceRepository] interface for communicating to and from
 * data sources
 */
class PlaceRepositoryImpl @Inject constructor(private val factory: PlaceDataStoreFactory,
                                              private val placeMapper: PlaceMapper) :
    PlaceRepository {


    override suspend fun getPlacesAutocomplete(queryString: String): ResultWrapper<List<Place>> {

        val response =
            factory.retrieveDataStore(false).getPlacesAutocomplete(  queryString)
        return when (response) {
            is ErrorWrapper.RespError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val places = arrayListOf<Place>()
                response.value.forEach {
                    places.add(placeMapper.mapFromEntity(it))
                }
                ResultWrapper.Success(places)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }

    }


}