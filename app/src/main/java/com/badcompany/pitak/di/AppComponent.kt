package com.badcompany.pitak.di

import android.app.Application
import com.badcompany.pitak.di.auth.AuthComponent
import com.badcompany.pitak.di.main.MainComponent
import com.badcompany.pitak.ui.BaseActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, SubComponentsModule::class])
interface AppComponent {

    @Component.Builder interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }


    fun inject(activity: BaseActivity)
    fun authComponent(): AuthComponent.Factory
    fun mainComponent(): MainComponent.Factory

}
