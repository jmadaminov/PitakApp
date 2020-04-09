package com.badcompany.pitak.di.login

import com.badcompany.pitak.ui.login.LoginActivity
import dagger.Subcomponent
import javax.inject.Singleton

@LoginScope
@Subcomponent(modules = [LoginModule::class, LoginViewModelModule::class])
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(loginActivity: LoginActivity)

}
