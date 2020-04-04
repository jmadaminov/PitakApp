package com.badcompany.domain.usecases

import com.badcompany.domain.ResultWrapper
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.domain.repository.IUserRepository


/** User Login Use Case
 *
 */
class LogUserIn(val repository: IUserRepository) : UseCaseWithParams<UserCredentials, ResultWrapper<Exception, String>>() {

    override suspend fun buildUseCase(params: UserCredentials): ResultWrapper<Exception, String> {
        return repository.loginUser(params)
    }

}