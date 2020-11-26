package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.repository.UserRepository


/** User Login Use Case
 *
 */
class LogUserIn(val repository: UserRepository) :
    UseCaseWithParams<HashMap<String, String>, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<String> {
        if (params[Constants.TXT_PHONE_NUMBER]!!.length != 12) {
            return ErrorWrapper.RespError(Constants.errPhoneFormat)
        }
        return repository.loginUser(params[Constants.TXT_PHONE_NUMBER]!!,
                                    params[Constants.TXT_UDID]!!)
    }

}