package com.badcompany.domain.repository

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.PassengerPost
import com.badcompany.domain.domainmodel.Place

interface PlaceRepository {

    suspend fun getPlacesAutocomplete(token: String,lang: String, queryString:String): ResultWrapper<List<Place>>


}