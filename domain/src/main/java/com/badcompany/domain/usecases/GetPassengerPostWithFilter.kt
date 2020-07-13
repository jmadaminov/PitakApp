package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Filter
import com.badcompany.domain.domainmodel.PassengerPost
import com.badcompany.domain.repository.PassengerPostRepository

/** User Login Use Case
 *
 */
class GetPassengerPostWithFilter(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<List<PassengerPost>>>() {

    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<List<PassengerPost>> {
        return repositoryPassenger.filterPassengerPost(params[Constants.TXT_TOKEN] as String,
                                                       params[Constants.TXT_LANG] as String,
                                                       params[Constants.TXT_FILTER] as Filter)
    }
}