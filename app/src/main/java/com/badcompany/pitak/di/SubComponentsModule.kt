package com.badcompany.pitak.di

import com.badcompany.pitak.di.login.LoginComponent
import com.badcompany.pitak.di.register.RegisterComponent
import dagger.Module

/**
 * Created by jahon on 09-Apr-20
 */

@Module(subcomponents = [LoginComponent::class, RegisterComponent::class])
class SubComponentsModule