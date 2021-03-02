package com.novatec.domain.usecases

import com.novatec.core.Constants
import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.domain.repository.DriverPostRepository


/** User Login Use Case
 *
 */
class DeleteDriverPost(val repositoryDriver: DriverPostRepository) :
    UseCaseWithParams<String, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: String): ResultWrapper<String> {
        return repositoryDriver.deleteDriverPost(params)
    }
}