package com.badcompany.data

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.mapper.AuthMapper
import com.badcompany.data.mapper.UserCredentialsMapper
import com.badcompany.data.mapper.UserMapper
import com.badcompany.data.source.UserDataStoreFactory
import com.badcompany.domain.domainmodel.AuthBody
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.User
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [UserRepository] interface for communicating to and from
 * data sources
 */
class UserRepositoryImpl @Inject constructor(private val factory: UserDataStoreFactory,
                                             private val userMapper: UserMapper,
                                             private val userCredentialsMapper: UserCredentialsMapper,
                                             private val authMapper: AuthMapper) :
    UserRepository {

    override suspend fun loginUser(phoneNum: String): ResultWrapper<String> {
        return factory.retrieveDataStore(false).userLogin(phoneNum)
    }

    override suspend fun registerUser(user: User): ResultWrapper<String> {
        return factory.retrieveDataStore(false).userRegister(userMapper.mapToEntity(user))
    }

    override suspend fun smsConfirm(userCredentials: UserCredentials): ResultWrapper<AuthBody> {

        val response = factory.retrieveDataStore(false)
            .confirmSms(userCredentialsMapper.mapToEntity(userCredentials))

        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> ResultWrapper.Success(authMapper.mapFromEntity(response.value))
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }

//        return factory.retrieveDataStore(false).confirmSms(userCredentialsMapper.mapToEntity(userCredentials))
    }


    override fun updateUserDetails(user: User): ResultWrapper<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOrUpdateUserCar(car: Car): ResultWrapper<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserCars(userId: String): ResultWrapper<List<Car>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUserCar(carId: String): ResultWrapper<List<Car>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}