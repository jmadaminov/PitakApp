package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.repository.DriverPostRepository


/** User Login Use Case
 *
 */
class FinishDriverPost(val repositoryDriver: DriverPostRepository) :
    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<Int>>() {

    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<Int> {
        val response = repositoryDriver.finishDriverPost(params[Constants.TXT_TOKEN] as String,
                                                         params[Constants.TXT_ID] as String)

        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> ResultWrapper.Success(params[Constants.TXT_POSITION] as Int)
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }
}