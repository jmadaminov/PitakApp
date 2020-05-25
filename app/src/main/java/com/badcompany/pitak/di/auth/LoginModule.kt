package com.badcompany.pitak.di.auth

import com.badcompany.domain.repository.UserRepository
import com.badcompany.domain.usecases.LogUserIn
import com.badcompany.domain.usecases.SmsConfirm
import dagger.Module
import dagger.Provides

@Module
object LoginModule {

    @AuthScope
    @Provides
    @JvmStatic
    fun provideLogUserInUseCase(userRepository: UserRepository): LogUserIn {
        return LogUserIn(userRepository)
    }


    @AuthScope
    @Provides
    @JvmStatic
    fun provideSmsConfirm(userRepository: UserRepository): SmsConfirm {
        return SmsConfirm(userRepository)
    }

}