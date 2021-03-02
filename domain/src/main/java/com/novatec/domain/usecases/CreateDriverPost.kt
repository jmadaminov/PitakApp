package com.novatec.domain.usecases

import com.novatec.core.Constants
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.repository.DriverPostRepository


/** User Login Use Case
 *
 */
class CreateDriverPost(val repositoryDriver: DriverPostRepository) :
    UseCaseWithParams<DriverPost, ResultWrapper<DriverPost>>() {

    override suspend fun buildUseCase(params: DriverPost): ResultWrapper<DriverPost> {
        return repositoryDriver.createDriverPost(params)

    }
}