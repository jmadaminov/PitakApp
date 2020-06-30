package com.badcompany.pitak.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.viewmodels.MainViewModelFactory
import com.badcompany.pitak.ui.main.MainViewModel
import com.badcompany.pitak.ui.main.mytrips.MyTripsViewModel
import com.badcompany.pitak.ui.main.mytrips.activetrips.ActiveTripsViewModel
import com.badcompany.pitak.ui.main.mytrips.historytrips.HistoryTripsViewModel
import com.badcompany.pitak.ui.main.profile.ProfileViewModel
import com.badcompany.pitak.ui.main.searchtrip.SearchTripViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: MainViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @MainViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @MainViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @MainViewModelKey(SearchTripViewModel::class)
    abstract fun bindSearchTripViewModel(viewModel: SearchTripViewModel): ViewModel

    @Binds
    @IntoMap
    @MainViewModelKey(MyTripsViewModel::class)
    abstract fun bindMyTripsViewModel(viewModel: MyTripsViewModel): ViewModel

    @Binds
    @IntoMap
    @MainViewModelKey(ActiveTripsViewModel::class)
    abstract fun bindActiveTripsViewModel(viewModel: ActiveTripsViewModel): ViewModel

    @Binds
    @IntoMap
    @MainViewModelKey(HistoryTripsViewModel::class)
    abstract fun bindHistoryTripsViewModel(viewModel: HistoryTripsViewModel): ViewModel

}