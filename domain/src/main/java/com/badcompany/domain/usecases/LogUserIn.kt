package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.repository.UserRepository


/** User Login Use Case
 *
 */
class LogUserIn(val repository: UserRepository) :
    UseCaseWithParams<String, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: String): ResultWrapper<String> {
        if (params.length != 12) {
            return ErrorWrapper.ResponseError(Constants.errPhoneFormat)
        }
        return repository.loginUser(params)
    }

}