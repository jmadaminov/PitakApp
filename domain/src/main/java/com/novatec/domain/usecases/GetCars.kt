package com.novatec.domain.usecases

import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.Car
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCars(val repository: CarRepository) :
    UseCase<ResultWrapper<List<CarDetails>>>() {

    override suspend fun buildUseCase(): ResultWrapper<List<CarDetails>> {
        return repository.getCars()
    }
}