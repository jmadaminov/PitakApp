package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.repository.DriverPostRepository


/** User Login Use Case
 *
 */
class DeleteDriverPost(val repositoryDriver: DriverPostRepository) :
    UseCaseWithParams<String, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: String): ResultWrapper<String> {
        return repositoryDriver.deleteDriverPost(params)
    }
}