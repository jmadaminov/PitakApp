package com.badcompany.pitak.di.register

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
import javax.inject.Singleton

@Module
object RegisterModule {

//    @Singleton
//    @Provides
//    @JvmStatic
//    fun provideRequestOptions(): RequestOptions {
//        return RequestOptions
//            .placeholderOf(R.drawable.white_background)
//            .error(R.drawable.white_background)
//    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUser {
        return RegisterUser(userRepository)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserRepository(factory: UserDataStoreFactory,
                              userMapper: UserMapper,
                              userCredentialsMapper: UserCredentialsMapper): UserRepository {
        return UserRepositoryImpl(factory, userMapper, userCredentialsMapper)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserCredentialsMapper(): UserCredentialsMapper {
        return UserCredentialsMapper()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserDataStoreFactory(userRemoteDataStore: UserRemoteDataStore): UserDataStoreFactory {
        return UserDataStoreFactory(userRemoteDataStore)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserRemoteDataStore(userRemote: UserRemote): UserRemoteDataStore {
        return UserRemoteDataStore(userRemote)
    }


    @Singleton
    @Provides
    @JvmStatic
    fun provideUserRemote(apiService: ApiService,
                          userCredMapper: com.badcompany.remote.mapper.UserCredentialsMapper): UserRemote {
        return UserRemoteImpl(apiService, userCredMapper)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideRemoteUserCredentialsMapper(): com.badcompany.remote.mapper.UserCredentialsMapper {
        return com.badcompany.remote.mapper.UserCredentialsMapper()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideApiService(): ApiService {
        return ApiServiceFactory.makeApiService(BuildConfig.DEBUG)
    }

}