package com.badcompany.pitak.di.login

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.login.LoginViewModelKey
import com.badcompany.pitak.di.viewmodels.LoginViewModelFactory
import com.badcompany.pitak.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoginViewModelModule {

    @LoginScope
    @Binds
    abstract fun bindViewModelFactory(factory: LoginViewModelFactory) : ViewModelProvider.Factory


    @LoginScope
    @Binds
    @IntoMap
    @LoginViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

}