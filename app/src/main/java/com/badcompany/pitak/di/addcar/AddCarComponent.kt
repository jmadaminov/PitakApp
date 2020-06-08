package com.badcompany.pitak.di.addcar

import com.badcompany.pitak.ui.addcar.AddCarActivity
import dagger.Subcomponent


@AddCarScope
@Subcomponent(modules = [AddCarModule::class, AddCarViewModelModule::class])
interface AddCarComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AddCarComponent
    }

    fun inject(addCarActivity: AddCarActivity)

}
