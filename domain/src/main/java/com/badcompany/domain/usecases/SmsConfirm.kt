package com.badcompany.domain.usecases

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.AuthBody
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.domain.repository.UserRepository


/** User Login Use Case
 *
 */
class SmsConfirm(val repository: UserRepository) :
    UseCaseWithParams<UserCredentials, ResultWrapper<AuthBody>>() {

    override suspend fun buildUseCase(params: UserCredentials): ResultWrapper<AuthBody> {
        return repository.smsConfirm(params)
    }

}