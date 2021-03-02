package com.novatec.pitak


//import com.novatec.pitak.di.AppComponent
//import com.novatec.pitak.di.DaggerAppComponent
//import com.novatec.pitak.di.addPost.AddPostComponent
//import com.novatec.pitak.di.addcar.AddCarComponent
//import com.novatec.pitak.di.auth.AuthComponent
//import com.novatec.pitak.di.main.MainComponent
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by jahon on 13-Mar-18.
 */

@HiltAndroidApp
class App : Application() {


    companion object {
        lateinit var uuid: String
        lateinit var INSTANCE: App
        lateinit var versionName: String

        fun getResources() = INSTANCE.resources
        fun getAppContext() = INSTANCE
        fun getInstance(): Context = INSTANCE
    }


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.NONE)

        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .setNotificationOpenedHandler(NotificationOpenHandler())
            .init()


        OneSignal.idsAvailable { userId, registrationId ->
            Log.i("USERRR IDD ONE SIGNAL", "            $userId")
            uuid = userId
        }

        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        versionName = info.versionName
    }

}


