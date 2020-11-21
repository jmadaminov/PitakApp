package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.repository.DriverPostRepository


/** User Login Use Case
 *
 */
class GetActiveDriverPost(val repositoryDriver: DriverPostRepository) :
    UseCase<ResultWrapper<List<DriverPost>>>() {

    override suspend fun buildUseCase(): ResultWrapper<List<DriverPost>> {
        return repositoryDriver.getActiveDriverPosts()
    }
}