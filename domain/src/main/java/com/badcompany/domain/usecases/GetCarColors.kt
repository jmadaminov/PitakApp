package com.badcompany.domain.usecases

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.CarColorBody
import com.badcompany.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCarColors(val repository: CarRepository) :
    UseCaseWithParams<String, ResultWrapper<List<CarColorBody>>>() {

    override suspend fun buildUseCase(params: String): ResultWrapper<List<CarColorBody>> {
        return repository.getCarColors(params)
    }
}