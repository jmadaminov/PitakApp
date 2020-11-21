package com.badcompany.domain.usecases

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Filter
import com.badcompany.domain.domainmodel.PassengerPost
import com.badcompany.domain.repository.PassengerPostRepository

/** User Login Use Case
 *
 */
class GetPassengerPostWithFilter(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<Filter, ResultWrapper<List<PassengerPost>>>() {

    override suspend fun buildUseCase(params: Filter): ResultWrapper<List<PassengerPost>> {
        return repositoryPassenger.filterPassengerPost(params)
    }
}