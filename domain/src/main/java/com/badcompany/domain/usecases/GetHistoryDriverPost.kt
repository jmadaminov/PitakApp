package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.repository.DriverPostRepository


/** User Login Use Case
 *
 */
class GetHistoryDriverPost(val repositoryDriver: DriverPostRepository) :
    UseCaseWithParams<Int, ResultWrapper<List<DriverPost>>>() {

    override suspend fun buildUseCase(params: Int): ResultWrapper<List<DriverPost>> {
        return repositoryDriver.getHistoryDriverPosts(params)
    }
}