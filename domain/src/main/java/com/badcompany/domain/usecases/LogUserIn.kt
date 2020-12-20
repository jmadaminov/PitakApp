package com.badcompany.domain.usecases

import com.badcompany.core.*
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.domain.repository.UserRepository


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