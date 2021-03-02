package com.novatec.domain.usecases

import com.novatec.core.Constants
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.repository.DriverPostRepository


/** User Login Use Case
 *
 */
class GetActiveDriverPost(val repositoryDriver: DriverPostRepository) :
    UseCase<ResultWrapper<List<DriverPost>>>() {

    override suspend fun buildUseCase(): ResultWrapper<List<DriverPost>> {
        return repositoryDriver.getActiveDriverPosts()
    }
}