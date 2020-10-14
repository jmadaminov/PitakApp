package com.badcompany.pitak

import android.content.Intent
import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal.NotificationOpenedHandler
import splitties.activities.start

 class NotificationOpenHandler : NotificationOpenedHandler {
    companion object {
        const val EXTRA_DISEASE_ID = "disease_id"
    }


     init{

     }


    // This fires when a notification is opened by tapping on it.
    override fun notificationOpened(result: OSNotificationOpenResult) {
        val actionType = result.action.type
        val data = result.notification.payload.additionalData

//        val diseaseId: Int? = data?.optInt(EXTRA_DISEASE_ID)
//
//        if (diseaseId != null && diseaseId != 0) {
//           App.getInstance()?.startActivity(Intent(App.getInstance(), MainActivity::class.java).apply {
//                    putExtra(EXTRA_DISEASE_ID, diseaseId)
//                    addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                })
//        } else {
//            App.getInstance()?.start<MainActivity> {
//                addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            }
//        }


    }
}