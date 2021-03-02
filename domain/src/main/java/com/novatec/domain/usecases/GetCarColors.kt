package com.novatec.domain.usecases

import com.novatec.core.Constants
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.CarColorBody
import com.novatec.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCarColors(val repository: CarRepository) :
    UseCase< ResultWrapper<List<CarColorBody>>>() {

    override suspend fun buildUseCase(): ResultWrapper<List<CarColorBody>> {
        return repository.getCarColors()
    }
}