package com.badcompany.pitak


import android.app.Application
import com.badcompany.pitak.di.AppComponent
import com.badcompany.pitak.di.DaggerAppComponent
import com.badcompany.pitak.di.login.LoginComponent
import com.badcompany.pitak.di.register.RegisterComponent

/**
 * Created by jahon on 13-Mar-18.
 */

open class App : Application() {

    lateinit var appComponent: AppComponent

    private var registerComponent: RegisterComponent? = null

    private var loginComponent: LoginComponent? = null

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    fun releaseLoginComponent() {
        loginComponent = null
    }

    fun loginComponent(): LoginComponent {
        if (loginComponent == null) {
            loginComponent = appComponent.loginComponent().create()
        }
        return loginComponent as LoginComponent
    }

    fun releaseRegisterComponent() {
        registerComponent = null
    }

    fun registerComponent(): RegisterComponent {
        if (registerComponent == null) {
            registerComponent = appComponent.registerComponent().create()
        }
        return registerComponent as RegisterComponent
    }

    fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}


