package com.badcompany.domain.usecases

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.repository.CarRepository


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