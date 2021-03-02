package com.novatec.domain.repository

import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.PassengerPost
import com.novatec.domain.domainmodel.Place

interface PlaceRepository {

    suspend fun getPlacesAutocomplete( queryString:String): ResultWrapper<List<Place>>


}