package com.badcompany.pitak.di.main

import com.badcompany.pitak.ui.main.MainActivity
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
