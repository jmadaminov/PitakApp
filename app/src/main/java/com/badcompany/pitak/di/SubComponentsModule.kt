package com.badcompany.pitak.di

import com.badcompany.pitak.di.addcar.AddCarComponent
import com.badcompany.pitak.di.auth.AuthComponent
import com.badcompany.pitak.di.main.MainComponent
import dagger.Module

/**
 * Created by jahon on 09-Apr-20
 */

@Module(subcomponents = [AuthComponent::class, MainComponent::class, AddCarComponent::class])
class SubComponentsModule