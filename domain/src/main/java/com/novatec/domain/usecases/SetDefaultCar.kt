package com.novatec.domain.usecases

import com.novatec.core.Constants
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.Car
import com.novatec.domain.domainmodel.CarColorBody
import com.novatec.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class SetDefaultCar(val repository: CarRepository) :
    UseCaseWithParams<Long, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: Long): ResultWrapper<String> {
        return repository.setDefaultCar(params )
    }
}