package com.badcompany.pitak.hilt
//import com.example.benefit.ui.auth.LoginFragmentFactory
import com.badcompany.data.*
import com.badcompany.data.mapper.*
import com.badcompany.data.repository.*
import com.badcompany.data.source.*
import com.badcompany.domain.repository.*
import com.badcompany.domain.usecases.*
import com.badcompany.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideLogUserInUseCase(userRepository: UserRepository): LogUserIn {
        return LogUserIn(userRepository)
    }


    @Provides
    @Singleton
    fun provideSmsConfirm(userRepository: UserRepository): SmsConfirm {
        return SmsConfirm(userRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUser {
        return RegisterUser(userRepository)
    }


    @Provides
    @Singleton
    fun provideUserRepository(factory: UserDataStoreFactory,
                              userMapper: UserMapper,
                              userCredentialsMapper: UserCredentialsMapper,
                              authMapper: AuthMapper): UserRepository {
        return UserRepositoryImpl(factory, userMapper, userCredentialsMapper, authMapper)
    }

    @Provides
    @Singleton
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }

    @Provides
    @Singleton
    fun provideAuthMapper(): AuthMapper {
        return AuthMapper()
    }

    @Provides
    @Singleton
    fun provideUserCredentialsMapper(): UserCredentialsMapper {
        return UserCredentialsMapper()
    }

    @Provides
    @Singleton
    fun provideUserDataStoreFactory(userRemoteDataStore: UserRemoteDataStore): UserDataStoreFactory {
        return UserDataStoreFactory(userRemoteDataStore)
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataStore(userRemote: UserRemote): UserRemoteDataStore {
        return UserRemoteDataStore(userRemote)
    }

    @Provides
    @Singleton
    fun provideUserRemote(apiService: ApiService,
                          userCredMapper: com.badcompany.remote.mapper.UserCredentialsMapper,
                          userMapper: com.badcompany.remote.mapper.UserMapper,
                          authMapper: com.badcompany.remote.mapper.AuthMapper): UserRemote {
        return UserRemoteImpl(apiService, userCredMapper, userMapper, authMapper)
    }


    @Provides
    @Singleton
    fun provideRemoteUserCredentialsMapper(): com.badcompany.remote.mapper.UserCredentialsMapper {
        return com.badcompany.remote.mapper.UserCredentialsMapper()
    }


    @Provides
    @Singleton
    fun provideRemoteUserMapper(): com.badcompany.remote.mapper.UserMapper {
        return com.badcompany.remote.mapper.UserMapper()
    }


    @Provides
    @Singleton
    fun provideRemoteAuthMapper(): com.badcompany.remote.mapper.AuthMapper {
        return com.badcompany.remote.mapper.AuthMapper()
    }


}




