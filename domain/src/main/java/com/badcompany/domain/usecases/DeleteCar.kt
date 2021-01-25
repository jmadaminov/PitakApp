package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.CarDetails
import com.badcompany.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class DeleteCar(val repository: CarRepository) :
    UseCaseWithParams<Long, ResultWrapper<List<CarDetails>>>() {

    override suspend fun buildUseCase(params: Long): ResultWrapper<List<CarDetails>> {
        return repository.deleteCar(params)

    }
}