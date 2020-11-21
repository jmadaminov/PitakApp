package com.badcompany.data

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.mapper.PlaceMapper
import com.badcompany.data.source.PlaceDataStoreFactory
import com.badcompany.domain.domainmodel.Place
import com.badcompany.domain.repository.PlaceRepository
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
            is ErrorWrapper.ResponseError -> response
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