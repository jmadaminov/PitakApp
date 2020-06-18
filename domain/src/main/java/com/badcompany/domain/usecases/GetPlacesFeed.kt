package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Place
import com.badcompany.domain.repository.PlaceRepository


/** User Login Use Case
 *
 */
class GetPlacesFeed(val repository: PlaceRepository) :
    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<Place>>>() {

    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<Place>> {
        return repository.getPlacesAutocomplete(params[Constants.TXT_TOKEN]!!,
                                                params[Constants.TXT_LANG]!!,
                                                params[Constants.TXT_PLACE]!!)
    }
}