package com.badcompany.data

import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.data.mapper.UserCredentialsMapper
import com.badcompany.data.mapper.UserMapper
import com.badcompany.data.source.UserDataStoreFactory
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.User
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [BufferooRepository] interface for communicating to and from
 * data sources
 */
class UserRepositoryImpl @Inject constructor(private val factory: UserDataStoreFactory,
                                             private val userMapper: UserMapper,
                                             private val userCredentialsMapper: UserCredentialsMapper) :
    UserRepository {
    override suspend fun loginUser(userCredentials: UserCredentials): ResultWrapper<ErrorWrapper, String> {
        return factory.retrieveDataStore(false).userLogin(userCredentialsMapper.mapToEntity(userCredentials) )
    }

    override suspend fun registerUser(user: User): ResultWrapper<ErrorWrapper, String> {
        return factory.retrieveDataStore(false).userRegister(userMapper.mapToEntity(user) )
    }


    override fun updateUserDetails(user: User): ResultWrapper<Exception, Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addOrUpdateUserCar(car: Car): ResultWrapper<Exception, Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserCars(userId: String): ResultWrapper<Exception, List<Car>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUserCar(carId: String): ResultWrapper<Exception, List<Car>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



//    override fun clearBufferoos(): Completable {
//        return factory.retrieveCacheDataStore().clearBufferoos()
//    }
//
//    override fun saveBufferoos(bufferoos: List<Bufferoo>): Completable {
//        val bufferooEntities = mutableListOf<BufferooEntity>()
//        bufferoos.map { bufferooEntities.add(bufferooMapper.mapToEntity(it)) }
//        return factory.retrieveCacheDataStore().saveBufferoos(bufferooEntities)
//    }
//
//    override fun getBufferoos(): Flowable<List<Bufferoo>> {
//        return factory.retrieveCacheDataStore().isCached()
//            .flatMapPublisher {
//                factory.retrieveDataStore(it).getBufferoos()
//            }
//            .flatMap {
//                Flowable.just(it.map { bufferooMapper.mapFromEntity(it) })
//            }
//            .flatMap {
//                saveBufferoos(it).toSingle { it }.toFlowable()
//            }
//    }

}