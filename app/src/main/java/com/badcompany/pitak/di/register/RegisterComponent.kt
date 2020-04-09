package com.badcompany.pitak.di.register

import com.badcompany.pitak.ui.register.RegisterActivity
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton


@RegisterScope
@Subcomponent(modules = [RegisterModule::class, RegisterViewModelModule::class])
interface RegisterComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RegisterComponent
    }

    fun inject(registerActivity: RegisterActivity)

}
