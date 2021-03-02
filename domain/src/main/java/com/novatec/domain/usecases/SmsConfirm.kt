package com.novatec.domain.usecases

import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.AuthBody
import com.novatec.domain.domainmodel.UserCredentials
import com.novatec.domain.repository.UserRepository


/** User Login Use Case
 *
 */
class SmsConfirm(val repository: UserRepository) :
    UseCaseWithParams<UserCredentials, ResultWrapper<AuthBody>>() {

    override suspend fun buildUseCase(params: UserCredentials): ResultWrapper<AuthBody> {
        return repository.smsConfirm(params)
    }

}