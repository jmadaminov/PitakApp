package com.badcompany.pitak.di.addcar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.viewmodels.AddCarViewModelFactory
import com.badcompany.pitak.ui.addcar.AddCarViewModel
import com.badcompany.pitak.ui.auth.confirm.PhoneConfirmViewModel
import com.badcompany.pitak.ui.auth.login.LoginViewModel
import com.badcompany.pitak.ui.auth.register.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddCarViewModelModule {

    @AddCarScope
    @Binds
    abstract fun bindViewModelFactory(factory: AddCarViewModelFactory) : ViewModelProvider.Factory

    @AddCarScope
    @Binds
    @IntoMap
    @AddCarViewModelKey(AddCarViewModel::class)
    abstract fun bindAddCarViewModel(viewModel: AddCarViewModel): ViewModel




}