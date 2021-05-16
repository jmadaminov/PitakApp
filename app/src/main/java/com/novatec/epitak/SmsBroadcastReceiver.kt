package com.novatec.epitak

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


class SmsBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent!!.action) {
            val extras = intent.extras
            val status: Status = extras!![SmsRetriever.EXTRA_STATUS] as Status
            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {    // Get SMS message contents
                    var message: String? = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String?

                }
                CommonStatusCodes.TIMEOUT -> {
                }
            }
        }
    }
}