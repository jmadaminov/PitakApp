package com.badcompany.pitak.di.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.viewmodels.RegisterViewModelFactory
import com.badcompany.pitak.ui.register.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RegisterViewModelModule {

    @RegisterScope
    @Binds
    abstract fun bindViewModelFactory(factory: RegisterViewModelFactory): ViewModelProvider.Factory


    @RegisterScope
    @Binds
    @IntoMap
    @RegisterViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

}