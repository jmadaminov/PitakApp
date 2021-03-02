package com.novatec.domain.usecases

import com.novatec.core.Constants
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.CarModelBody
import com.novatec.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCarModels(val repository: CarRepository) :
    UseCase< ResultWrapper<List<CarModelBody>>>() {

    override suspend fun buildUseCase(): ResultWrapper<List<CarModelBody>> {
        return repository.getCarModels()
    }
}