package com.badcompany.pitak.di.addPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.viewmodels.AddPostViewModelFactory
import com.badcompany.pitak.ui.addpost.AddPostViewModel
import com.badcompany.pitak.ui.addpost.destinations.DestinationsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddPostViewModelModule {

    @AddPostScope
    @Binds
    abstract fun bindViewModelFactory(factory: AddPostViewModelFactory): ViewModelProvider.Factory

    @AddPostScope
    @Binds
    @IntoMap
    @AddPostViewModelKey(AddPostViewModel::class)
    abstract fun bindAddPostViewModel(viewModel: AddPostViewModel): ViewModel

    @AddPostScope
    @Binds
    @IntoMap
    @AddPostViewModelKey(DestinationsViewModel::class)
    abstract fun bindChooseDestinationsViewModel(viewModel: DestinationsViewModel): ViewModel


}