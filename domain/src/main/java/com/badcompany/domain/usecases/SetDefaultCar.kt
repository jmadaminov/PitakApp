package com.badcompany.domain.usecases

import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.CarColorBody
import com.badcompany.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class SetDefaultCar(val repository: CarRepository) :
    UseCaseWithParams<Long, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: Long): ResultWrapper<String> {
        return repository.setDefaultCar(params )
    }
}