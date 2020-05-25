package com.badcompany.domain.usecases

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.CarModelBody
import com.badcompany.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCarModels(val repository: CarRepository) :
    UseCaseWithParams<String, ResultWrapper<List< CarModelBody>>>() {

    override suspend fun buildUseCase(params: String): ResultWrapper<List< CarModelBody>> {
        return repository.getCarModels(params)
    }
}