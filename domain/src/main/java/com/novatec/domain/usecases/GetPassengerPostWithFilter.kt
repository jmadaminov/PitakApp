package com.novatec.domain.usecases

import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.Filter
import com.novatec.domain.domainmodel.PassengerPost
import com.novatec.domain.repository.PassengerPostRepository

/** User Login Use Case
 *
 */
class GetPassengerPostWithFilter(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<Filter, ResultWrapper<List<PassengerPost>>>() {

    override suspend fun buildUseCase(params: Filter): ResultWrapper<List<PassengerPost>> {
        return repositoryPassenger.filterPassengerPost(params)
    }
}