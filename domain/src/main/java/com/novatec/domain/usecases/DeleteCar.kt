package com.novatec.domain.usecases

import com.novatec.core.Constants
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class DeleteCar(val repository: CarRepository) :
    UseCaseWithParams<Long, ResultWrapper<List<CarDetails>>>() {

    override suspend fun buildUseCase(params: Long): ResultWrapper<List<CarDetails>> {
        return repository.deleteCar(params)

    }
}