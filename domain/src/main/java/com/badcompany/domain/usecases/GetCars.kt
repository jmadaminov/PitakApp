package com.badcompany.domain.usecases

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.CarDetails
import com.badcompany.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCars(val repository: CarRepository) :
    UseCaseWithParams<String, ResultWrapper<List<CarDetails>>>() {

    override suspend fun buildUseCase(params: String): ResultWrapper<List<CarDetails>> {
        return repository.getCars(params)
    }
}