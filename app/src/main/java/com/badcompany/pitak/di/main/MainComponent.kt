package com.badcompany.pitak.di.main

import com.badcompany.pitak.MainActivity
import com.badcompany.pitak.ui.BaseFragment
import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [MainModule::class, MainViewModelModule::class, MainFragmentsModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
//    fun inject(fragment: BaseFragment)

}
