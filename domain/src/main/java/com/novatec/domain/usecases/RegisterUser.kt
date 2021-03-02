package com.novatec.domain.usecases

import com.novatec.core.Constants
import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.User
import com.novatec.domain.repository.UserRepository


/** User Login Use Case
 *
 */
class RegisterUser(val repository: UserRepository) :
    UseCaseWithParams<User, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: User): ResultWrapper<String> {
        if (params.phoneNum.length != 12) return ErrorWrapper.RespError(Constants.errPhoneFormat)
        return repository.registerUser(params)
    }

}