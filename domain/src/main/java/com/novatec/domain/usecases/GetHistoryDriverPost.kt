package com.novatec.domain.usecases

import com.novatec.core.Constants
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.repository.DriverPostRepository


/** User Login Use Case
 *
 */
class GetHistoryDriverPost(val repositoryDriver: DriverPostRepository) :
    UseCaseWithParams<Int, ResultWrapper<List<DriverPost>>>() {

    override suspend fun buildUseCase(params: Int): ResultWrapper<List<DriverPost>> {
        return repositoryDriver.getHistoryDriverPosts(params)
    }
}