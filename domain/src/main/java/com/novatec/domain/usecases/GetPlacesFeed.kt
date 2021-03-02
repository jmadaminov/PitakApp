package com.novatec.domain.usecases

import com.novatec.core.Constants
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.Place
import com.novatec.domain.repository.PlaceRepository


/** User Login Use Case
 *
 */
class GetPlacesFeed(val repository: PlaceRepository) :
    UseCaseWithParams<String, ResultWrapper<List<Place>>>() {

    override suspend fun buildUseCase(params: String): ResultWrapper<List<Place>> {
        return repository.getPlacesAutocomplete(                                                params)
    }
}