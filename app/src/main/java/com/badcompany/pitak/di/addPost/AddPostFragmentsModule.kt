package com.badcompany.pitak.di.addPost

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.fragments.AddPostFragmentFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object AddPostFragmentsModule {


    @JvmStatic
    @AddPostScope
    @Provides
    @Named("AddPostFragmentFactory")
    fun provideAddPostFragmentFactory(viewModelFactory: ViewModelProvider.Factory): FragmentFactory {
        return AddPostFragmentFactory(viewModelFactory)
    }


}