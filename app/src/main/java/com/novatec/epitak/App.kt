package com.novatec.epitak


import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import com.novatec.core.ENotificationType
import com.novatec.epitak.ui.driver_post.EXTRA_POST_ID
import com.novatec.epitak.ui.main.MainActivity
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import splitties.activities.start


/**
 * Created by jahon on 13-Mar-18.
 */
const val ONESIGNAL_APP_ID = "2a435933-109c-4a7c-822d-44a67e231c70"

@HiltAndroidApp
class App : Application() {


    companion object {
        var uuid: String? = null
        lateinit var INSTANCE: App
        lateinit var versionName: String

        fun getAppContext() = INSTANCE
        fun getInstance(): Context = INSTANCE

    }


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
//        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.setNotificationOpenedHandler { result ->
//            val actionId = result.action.actionId
//            val type: String = result.action.type // "ActionTaken" | "Opened"
//            val title = result.notification.title

            val actionType = result.action.type
            val data = result.notification.additionalData

            val notificationType = data["notificationType"]
            val postId: Long? = data?.optLong("postId")


            if (postId != null && notificationType == ENotificationType.OFFER_CREATE.name) {
                getInstance()
                    .startActivity(Intent(getInstance(), MainActivity::class.java).apply {
                        putExtra(EXTRA_POST_ID, postId)
                        addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    })
            } else {
                getInstance().start<MainActivity> {
                    addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }

        }

        OneSignal.getDeviceState()?.userId?.let {
            uuid = it
        } ?: run {
            OneSignal.addSubscriptionObserver { stateChanges ->
                if (!stateChanges!!.from.isSubscribed &&
                    stateChanges.to.isSubscribed
                ) {
                    // get player ID
                    uuid = stateChanges.to.userId
                }
                Log.i("Debug", "onOSPermissionChanged: $stateChanges")
            }
        }

        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        versionName = info.versionName
    }

}


