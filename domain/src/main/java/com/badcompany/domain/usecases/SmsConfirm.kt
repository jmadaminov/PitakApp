package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ErrorWrapper
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
//        if (params.length != 12) {
//            return ErrorWrapper.ResponseError(Constants.errPhoneFormat)
//        }
        return repository.smsConfirm(params)
    }

}