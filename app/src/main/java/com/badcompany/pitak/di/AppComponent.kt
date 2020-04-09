package com.badcompany.pitak.di

import android.app.Application
import com.badcompany.pitak.App
import com.badcompany.pitak.di.login.LoginComponent
import com.badcompany.pitak.di.register.RegisterComponent
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
    fun loginComponent(): LoginComponent.Factory
    fun registerComponent(): RegisterComponent.Factory

}
