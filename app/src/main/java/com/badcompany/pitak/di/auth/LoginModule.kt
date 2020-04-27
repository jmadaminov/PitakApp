package com.badcompany.pitak.di.auth

import com.badcompany.domain.repository.UserRepository
import com.badcompany.domain.usecases.LogUserIn
import dagger.Module
import dagger.Provides

@Module
object LoginModule {

    @AuthScope
    @Provides
    @JvmStatic
    fun provideRegisterUserUseCase(userRepository: UserRepository): LogUserIn {
        return LogUserIn(userRepository)
    }

}