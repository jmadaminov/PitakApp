package com.novatec.epitak.util

import android.content.Context
import splitties.preferences.edit
import java.util.*

object ContextUtils {
    fun setLocale(lang: String, context: Context) {
        AppPrefs.edit {
            language = lang
        }
        val myLocale = Locale(lang)
        Locale.setDefault(myLocale)
        val appConfig = context.resources.configuration
        appConfig.setLocale(myLocale)
        context.resources.updateConfiguration(appConfig, context.resources.displayMetrics)
    }
}