package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class SaveCar(val repository: CarRepository) :
    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<String> {
        return if ((params[Constants.TXT_CAR] as Car).id == null)
            repository.createCar(params[Constants.TXT_TOKEN] as String,
                                 params[Constants.TXT_CAR] as Car)
        else repository.updateCar(params[Constants.TXT_TOKEN] as String,
                                  params[Constants.TXT_CAR] as Car)

    }
}