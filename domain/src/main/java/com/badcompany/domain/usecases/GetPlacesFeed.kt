package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Place
import com.badcompany.domain.repository.PlaceRepository


/** User Login Use Case
 *
 */
class GetPlacesFeed(val repository: PlaceRepository) :
    UseCaseWithParams<String, ResultWrapper<List<Place>>>() {

    override suspend fun buildUseCase(params: String): ResultWrapper<List<Place>> {
        return repository.getPlacesAutocomplete(                                                params)
    }
}