package com.badcompany.domain.usecases

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.CarDetails
import com.badcompany.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCars(val repository: CarRepository) :
    UseCase<ResultWrapper<List<CarDetails>>>() {

    override suspend fun buildUseCase(): ResultWrapper<List<CarDetails>> {
        return repository.getCars()
    }
}