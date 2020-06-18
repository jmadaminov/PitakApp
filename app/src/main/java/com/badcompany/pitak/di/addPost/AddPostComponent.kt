package com.badcompany.pitak.di.addPost

import com.badcompany.pitak.ui.addpost.AddPostActivity
import dagger.Subcomponent


@AddPostScope
@Subcomponent(modules = [AddPostModule::class, AddPostViewModelModule::class, AddPostFragmentsModule::class])
interface AddPostComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AddPostComponent
    }

    fun inject(addPostActivity: AddPostActivity)

}
