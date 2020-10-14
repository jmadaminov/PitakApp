package com.badcompany.pitak


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
//import com.badcompany.pitak.di.AppComponent
//import com.badcompany.pitak.di.DaggerAppComponent
//import com.badcompany.pitak.di.addPost.AddPostComponent
//import com.badcompany.pitak.di.addcar.AddCarComponent
//import com.badcompany.pitak.di.auth.AuthComponent
//import com.badcompany.pitak.di.main.MainComponent
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by jahon on 13-Mar-18.
 */

@HiltAndroidApp
 class App : Application() {

//    lateinit var appComponent: AppComponent
//    private var authComponent: AuthComponent? = null
//    private var addCarComponent: AddCarComponent? = null
//    private var addPostComponent: AddPostComponent? = null
//    private var mainComponent: MainComponent? = null


    companion object {
        lateinit var uuid: String
        private var INSTANCE: App? = null

        const val TYPE_CONSUMER = 1
        const val TYPE_DOCTOR = 2

        fun getSharedPreferences(name: String, mode: Int): SharedPreferences? =
            INSTANCE?.getSharedPreferences(name, mode)

        fun getResources() = INSTANCE?.resources
        fun getAppContext() = INSTANCE
        fun getInstance(): Context? = INSTANCE
    }



    override fun onCreate() {
        super.onCreate()

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.NONE)

        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .setNotificationOpenedHandler( NotificationOpenHandler())
            .init()


        OneSignal.idsAvailable { userId, registrationId ->
            Log.i("USERRR IDD ONE SIGNAL", "            $userId")
            Log.i("USERRR IDD ONE SIGNAL", "            $userId")
            Log.i("USERRR IDD ONE SIGNAL", "            $userId")
            uuid = userId
        }

//        initAppComponent()
    }

//    fun releaseAuthComponent() {
//        authComponent = null
//    }
//
//    fun authComponent(): AuthComponent {
//        if (authComponent == null) {
//            authComponent = appComponent.authComponent().create()
//        }
//        return authComponent as AuthComponent
//    }
//
//    fun mainComponent(): MainComponent {
//        if (mainComponent == null) {
//            mainComponent = appComponent.mainComponent().create()
//        }
//        return mainComponent as MainComponent
//    }
//
//    fun releaseMainComponent() {
//        mainComponent == null
//    }
//
//    fun initAppComponent() {
//        appComponent = DaggerAppComponent.builder()
//            .application(this)
//            .build()
//    }
//
//    fun releaseAddCarComponent() {
//        addCarComponent = null
//    }
//
//    fun releaseAddPostComponent() {
//        addPostComponent = null
//    }
//
//    fun addCarComponent(): AddCarComponent {
//        if (addCarComponent == null) {
//            addCarComponent = appComponent.addCarComponent().create()
//        }
//        return addCarComponent as AddCarComponent
//    }
//
//
//    fun addPostComponent(): AddPostComponent {
//        if (addPostComponent == null) {
//            addPostComponent = appComponent.addPostComponent().create()
//        }
//        return addPostComponent as AddPostComponent
//    }


}


