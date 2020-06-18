package com.badcompany.pitak.di.auth

import com.badcompany.pitak.di.addPost.AddPostFragmentsModule
import com.badcompany.pitak.ui.auth.AuthActivity
import dagger.Subcomponent

@AuthScope
@Subcomponent(modules = [LoginModule::class, RegisterModule::class, AuthViewModelModule::class, AuthFragmentsModule::class])
interface AuthComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun inject(authActivity: AuthActivity)

}
