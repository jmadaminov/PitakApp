package com.novatec.pitak.hilt
//import com.example.benefit.ui.auth.LoginFragmentFactory
import com.novatec.data.UserRepositoryImpl
import com.novatec.data.mapper.AuthMapper
import com.novatec.data.mapper.UserCredentialsMapper
import com.novatec.data.mapper.UserMapper
import com.novatec.data.repository.UserRemote
import com.novatec.data.source.UserDataStoreFactory
import com.novatec.data.source.UserRemoteDataStore
import com.novatec.domain.repository.UserRepository
import com.novatec.domain.usecases.LogUserIn
import com.novatec.domain.usecases.RegisterUser
import com.novatec.domain.usecases.SmsConfirm
import com.novatec.pitak.BuildConfig
import com.novatec.pitak.util.AppPrefs
import com.novatec.remote.ApiService
import com.novatec.remote.ApiServiceFactory
import com.novatec.remote.AuthorizedApiService
import com.novatec.remote.UserRemoteImpl
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
//
//
//    @Provides
//    @Singleton
//    fun provideLogUserInUseCase(userRepository: UserRepository): LogUserIn {
//        return LogUserIn(userRepository)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideSmsConfirm(userRepository: UserRepository): SmsConfirm {
//        return SmsConfirm(userRepository)
//    }
//
//    @Provides
//    @Singleton
//    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUser {
//        return RegisterUser(userRepository)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideUserRepository(factory: UserDataStoreFactory,
//                              userMapper: UserMapper,
//                              userCredentialsMapper: UserCredentialsMapper,
//                              authMapper: AuthMapper): UserRepository {
//        return UserRepositoryImpl(factory, userMapper, userCredentialsMapper, authMapper)
//    }
//
//    @Provides
//    @Singleton
//    fun provideUserMapper(): UserMapper {
//        return UserMapper()
//    }
//
//    @Provides
//    @Singleton
//    fun provideAuthMapper(): AuthMapper {
//        return AuthMapper()
//    }
//
//    @Provides
//    @Singleton
//    fun provideUserCredentialsMapper(): UserCredentialsMapper {
//        return UserCredentialsMapper()
//    }
//
//    @Provides
//    @Singleton
//    fun provideUserDataStoreFactory(userRemoteDataStore: UserRemoteDataStore): UserDataStoreFactory {
//        return UserDataStoreFactory(userRemoteDataStore)
//    }
//
//    @Provides
//    @Singleton
//    fun provideUserRemoteDataStore(userRemote: UserRemote): UserRemoteDataStore {
//        return UserRemoteDataStore(userRemote)
//    }
//
//    @Provides
//    @Singleton
//    fun provideUserRemote(apiService: ApiService,
//                          userCredMapper: com.novatec.remote.mapper.UserCredentialsMapper,
//                          userMapper: com.novatec.remote.mapper.UserMapper,
//                          authMapper: com.novatec.remote.mapper.AuthMapper,
//                          authApiService: AuthorizedApiService): UserRemote {
//        return UserRemoteImpl(apiService, authApiService, userCredMapper, userMapper, authMapper)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideRemoteUserCredentialsMapper(): com.novatec.remote.mapper.UserCredentialsMapper {
//        return com.novatec.remote.mapper.UserCredentialsMapper()
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideRemoteUserMapper(): com.novatec.remote.mapper.UserMapper {
//        return com.novatec.remote.mapper.UserMapper()
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideRemoteAuthMapper(): com.novatec.remote.mapper.AuthMapper {
//        return com.novatec.remote.mapper.AuthMapper()
//    }
//

}




