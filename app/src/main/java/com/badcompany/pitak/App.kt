package com.badcompany.pitak


import android.app.Application
import com.badcompany.pitak.di.AppComponent
import com.badcompany.pitak.di.DaggerAppComponent
import com.badcompany.pitak.di.addPost.AddPostComponent
import com.badcompany.pitak.di.addcar.AddCarComponent
import com.badcompany.pitak.di.auth.AuthComponent
import com.badcompany.pitak.di.main.MainComponent

/**
 * Created by jahon on 13-Mar-18.
 */

open class App : Application() {

    lateinit var appComponent: AppComponent
    private var authComponent: AuthComponent? = null
    private var addCarComponent: AddCarComponent? = null
    private var addPostComponent: AddPostComponent? = null
    private var mainComponent: MainComponent? = null

    companion object {
         var udid = ""
    }


    override fun onCreate() {
        super.onCreate()
        initAppComponent()


    }

    fun releaseAuthComponent() {
        authComponent = null
    }

    fun authComponent(): AuthComponent {
        if (authComponent == null) {
            authComponent = appComponent.authComponent().create()
        }
        return authComponent as AuthComponent
    }

    fun mainComponent(): MainComponent {
        if (mainComponent == null) {
            mainComponent = appComponent.mainComponent().create()
        }
        return mainComponent as MainComponent
    }

    fun releaseMainComponent() {
        mainComponent == null
    }

    fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    fun releaseAddCarComponent() {
        addCarComponent = null
    }

    fun releaseAddPostComponent() {
        addPostComponent = null
    }

    fun addCarComponent(): AddCarComponent {
        if (addCarComponent == null) {
            addCarComponent = appComponent.addCarComponent().create()
        }
        return addCarComponent as AddCarComponent
    }


    fun addPostComponent(): AddPostComponent {
        if (addPostComponent == null) {
            addPostComponent = appComponent.addPostComponent().create()
        }
        return addPostComponent as AddPostComponent
    }


}


