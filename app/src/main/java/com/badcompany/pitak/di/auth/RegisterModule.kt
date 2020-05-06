package com.badcompany.pitak.di.auth

import com.badcompany.data.UserRepositoryImpl
import com.badcompany.data.mapper.UserCredentialsMapper
import com.badcompany.data.mapper.UserMapper
import com.badcompany.data.repository.UserRemote
import com.badcompany.data.source.UserDataStoreFactory
import com.badcompany.data.source.UserRemoteDataStore
import com.badcompany.domain.repository.UserRepository
import com.badcompany.domain.usecases.RegisterUser
import com.badcompany.pitak.BuildConfig
import com.badcompany.remote.ApiService
import com.badcompany.remote.ApiServiceFactory
import com.badcompany.remote.UserRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object RegisterModule {

    @AuthScope
    @Provides
    @JvmStatic
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUser {
        return RegisterUser(userRepository)
    }

    @AuthScope
    @Provides
    @JvmStatic
    fun provideUserRepository(factory: UserDataStoreFactory,
                              userMapper: UserMapper,
                              userCredentialsMapper: UserCredentialsMapper): UserRepository {
        return UserRepositoryImpl(factory, userMapper, userCredentialsMapper)
    }

    @Provides
    @AuthScope
    @JvmStatic
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }

    @Provides
    @AuthScope
    @JvmStatic
    fun provideUserCredentialsMapper(): UserCredentialsMapper {
        return UserCredentialsMapper()
    }

    @Provides
    @AuthScope
    @JvmStatic
    fun provideUserDataStoreFactory(userRemoteDataStore: UserRemoteDataStore): UserDataStoreFactory {
        return UserDataStoreFactory(userRemoteDataStore)
    }

    @Provides
    @AuthScope
    @JvmStatic
    fun provideUserRemoteDataStore(userRemote: UserRemote): UserRemoteDataStore {
        return UserRemoteDataStore(userRemote)
    }


    @Provides
    @AuthScope
    @JvmStatic
    fun provideUserRemote(apiService: ApiService,
                          userCredMapper: com.badcompany.remote.mapper.UserCredentialsMapper,
                          userMapper: com.badcompany.remote.mapper.UserMapper,
                          authMapper: com.badcompany.remote.mapper.AuthMapper): UserRemote {
        return UserRemoteImpl(apiService, userCredMapper, userMapper, authMapper)
    }

    @AuthScope
    @Provides
    @JvmStatic
    fun provideRemoteUserCredentialsMapper(): com.badcompany.remote.mapper.UserCredentialsMapper {
        return com.badcompany.remote.mapper.UserCredentialsMapper()
    }

    @AuthScope
    @Provides
    @JvmStatic
    fun provideRemoteUserMapper(): com.badcompany.remote.mapper.UserMapper {
        return com.badcompany.remote.mapper.UserMapper()
    }


    @AuthScope
    @Provides
    @JvmStatic
    fun provideRemoteAuthMapper(): com.badcompany.remote.mapper.AuthMapper {
        return com.badcompany.remote.mapper.AuthMapper()
    }

    @Provides
    @AuthScope
    @JvmStatic
    fun provideApiService(): ApiService {
        return ApiServiceFactory.makeApiService(BuildConfig.DEBUG)
    }

}