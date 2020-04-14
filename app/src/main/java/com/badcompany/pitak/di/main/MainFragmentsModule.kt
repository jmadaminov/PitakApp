package com.badcompany.pitak.di.main

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.fragments.ProfileFragmentFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object MainFragmentsModule {

    @JvmStatic
    @MainScope
    @Provides
    @Named("ProfileFragmentFactory")
    fun provideProfileFragmentFactory(viewModelFactory: ViewModelProvider.Factory): FragmentFactory {
        return ProfileFragmentFactory(viewModelFactory)
    }

//    @JvmStatic
//    @MainScope
//    @Provides
//    @Named("BlogFragmentFactory")
//    fun provideBlogFragmentFactory(
//        viewModelFactory: ViewModelProvider.Factory,
//        requestOptions: RequestOptions,
//        requestManager: RequestManager
//    ): FragmentFactory {
//        return BlogFragmentFactory(
//            viewModelFactory,
//            requestOptions,
//            requestManager
//        )
//    }
//
//    @JvmStatic
//    @MainScope
//    @Provides
//    @Named("CreateBlogFragmentFactory")
//    fun provideCreateBlogFragmentFactory(
//        viewModelFactory: ViewModelProvider.Factory,
//        requestManager: RequestManager
//    ): FragmentFactory {
//        return CreateBlogFragmentFactory(
//            viewModelFactory,
//            requestManager
//        )
//    }

}