package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.CarModelBody
import com.badcompany.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCarModels(val repository: CarRepository) :
    UseCase< ResultWrapper<List<CarModelBody>>>() {

    override suspend fun buildUseCase(): ResultWrapper<List<CarModelBody>> {
        return repository.getCarModels()
    }
}