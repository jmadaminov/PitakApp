package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.CarColorBody
import com.badcompany.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCarColors(val repository: CarRepository) :
    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<CarColorBody>>>() {

    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<CarColorBody>> {
        return repository.getCarColors(params[Constants.TXT_TOKEN]!!, params[Constants.TXT_LANG]!!)
    }
}