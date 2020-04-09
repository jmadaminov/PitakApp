package com.badcompany.domain.usecases

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.User
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.domain.repository.UserRepository


/** User Login Use Case
 *
 */
class RegisterUser(val repository: UserRepository) : UseCaseWithParams<User, ResultWrapper<Exception, String>>() {

    override suspend fun buildUseCase(params: User): ResultWrapper<Exception, String> {
        return repository.registerUser(params)
    }

}