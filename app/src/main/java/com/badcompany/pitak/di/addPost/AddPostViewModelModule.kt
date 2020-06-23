package com.badcompany.pitak.di.addPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.viewmodels.AddPostViewModelFactory
import com.badcompany.pitak.ui.addpost.AddPostViewModel
import com.badcompany.pitak.ui.addpost.carandtext.CarAndTextViewModel
import com.badcompany.pitak.ui.addpost.destinations.DestinationsViewModel
import com.badcompany.pitak.ui.addpost.preview.PreviewViewModel
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

    @Binds
    @IntoMap
    @AddPostViewModelKey(DestinationsViewModel::class)
    abstract fun bindChooseDestinationsViewModel(viewModel: DestinationsViewModel): ViewModel

    @Binds
    @IntoMap
    @AddPostViewModelKey(CarAndTextViewModel::class)
    abstract fun bindChooseCarAndTextViewModel(viewModel: CarAndTextViewModel): ViewModel

    @Binds
    @IntoMap
    @AddPostViewModelKey(PreviewViewModel::class)
    abstract fun bindChoosePreviewViewModel(viewModel: PreviewViewModel): ViewModel


}