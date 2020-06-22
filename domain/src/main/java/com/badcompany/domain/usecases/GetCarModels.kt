package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.CarModelBody
import com.badcompany.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCarModels(val repository: CarRepository) :
    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<CarModelBody>>>() {

    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<CarModelBody>> {
        return repository.getCarModels(params[Constants.TXT_TOKEN]!!, params[Constants.TXT_LANG]!!)
    }
}