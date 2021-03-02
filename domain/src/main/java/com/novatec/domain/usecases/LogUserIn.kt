package com.novatec.domain.usecases

import com.novatec.core.*
import com.novatec.domain.domainmodel.UserCredentials
import com.novatec.domain.repository.UserRepository


/** User Login Use Case
 *
 */
class LogUserIn(val repository: UserRepository) :
    UseCaseWithParams<String, ResponseWrapper<UserCredentials?>>() {

    override suspend fun buildUseCase(params: String): ResponseWrapper<UserCredentials?> {
        if (params.length != 12) {
            return ResponseError(code = Constants.errPhoneFormat)
        }
        return repository.loginUser(params)
    }

}