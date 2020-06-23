package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.repository.DriverPostRepository


/** User Login Use Case
 *
 */
class CreateDriverPost(val repositoryDriver: DriverPostRepository) :
    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<String> {
        return repositoryDriver.createDriverPost(params[Constants.TXT_TOKEN] as String,
                                                 params[Constants.TXT_DRIVER_POST] as DriverPost)

    }
}