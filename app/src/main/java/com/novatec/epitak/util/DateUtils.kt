package com.novatec.epitak.util

import android.content.Context
import android.text.format.DateFormat
import com.novatec.epitak.R
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getFormattedDate(smsTimeInMilis: Long, context: Context): String {
        val smsTime = Calendar.getInstance()
        smsTime.timeInMillis = smsTimeInMilis
        val now = Calendar.getInstance()
        val dateTimeFormatString = "dd.MM.yyyy"
        return when {
            now[Calendar.DATE] == smsTime[Calendar.DATE] -> {
                context.getString(R.string.today)
            }
            now[Calendar.DATE] - smsTime[Calendar.DATE] == -1 -> {
                context.getString(R.string.tomorrow)
            }
            else -> {
                DateFormat.format(dateTimeFormatString, Date(smsTimeInMilis)).toString()
            }
        }
    }

//    fun getFormattedDate(dateStringDDMMYY: String, context: Context): String {
//        val smsTime = Calendar.getInstance()
//        smsTime.timeInMillis = SimpleDateFormat("dd.MM.YY").parse(dateStringDDMMYY).time
//        val now = Calendar.getInstance()
//        val dateTimeFormatString = "dd.MM.yyyy"
//        return when {
//            now[Calendar.DATE] == smsTime[Calendar.DATE] -> {
//                context.getString(R.string.today)
//            }
//            now[Calendar.DATE] - smsTime[Calendar.DATE] == -1 -> {
//                context.getString(R.string.tomorrow)
//            }
//            else -> {
//                DateFormat.format(dateTimeFormatString, Date(smsTime.timeInMillis)).toString()
//            }
//        }
//    }
}