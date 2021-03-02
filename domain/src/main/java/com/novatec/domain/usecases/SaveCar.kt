package com.novatec.domain.usecases

import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.Car
import com.novatec.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class SaveCar(val repository: CarRepository) :
    UseCaseWithParams<Car, ResultWrapper<Car>>() {

    override suspend fun buildUseCase(params: Car): ResultWrapper<Car> {
        return if (params.id == null)
            repository.createCar(params)
        else repository.updateCar(params)

    }
}