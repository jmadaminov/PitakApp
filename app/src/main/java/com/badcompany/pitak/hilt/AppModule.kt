package com.badcompany.pitak.hilt
//import com.example.benefit.ui.auth.LoginFragmentFactory
import com.badcompany.data.UserRepositoryImpl
import com.badcompany.data.mapper.AuthMapper
import com.badcompany.data.mapper.UserCredentialsMapper
import com.badcompany.data.mapper.UserMapper
import com.badcompany.data.repository.UserRemote
import com.badcompany.data.source.UserDataStoreFactory
import com.badcompany.data.source.UserRemoteDataStore
import com.badcompany.domain.repository.UserRepository
import com.badcompany.domain.usecases.LogUserIn
import com.badcompany.domain.usecases.RegisterUser
import com.badcompany.domain.usecases.SmsConfirm
import com.badcompany.pitak.BuildConfig
import com.badcompany.pitak.util.AppPrefs
import com.badcompany.remote.ApiService
import com.badcompany.remote.ApiServiceFactory
import com.badcompany.remote.AuthorizedApiService
import com.badcompany.remote.UserRemoteImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import splitties.experimental.ExperimentalSplittiesApi
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
                          authMapper: com.badcompany.remote.mapper.AuthMapper,
                          authApiService: AuthorizedApiService): UserRemote {
        return UserRemoteImpl(apiService, authApiService, userCredMapper, userMapper, authMapper)
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




