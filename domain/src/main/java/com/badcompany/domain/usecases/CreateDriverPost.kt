package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.repository.DriverPostRepository


/** User Login Use Case
 *
 */
class CreateDriverPost(val repositoryDriver: DriverPostRepository) :
    UseCaseWithParams<DriverPost, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: DriverPost): ResultWrapper<String> {
        return repositoryDriver.createDriverPost(params)

    }
}