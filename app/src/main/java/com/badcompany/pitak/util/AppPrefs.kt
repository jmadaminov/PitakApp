package com.badcompany.pitak.util

/**
 * Created by jahon on 23-Apr-20
 */


import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.Preferences

@ExperimentalSplittiesApi object AppPrefs : Preferences("myPrefs") {

    var token by StringPref("TOKEN", "")
    var language by StringPref("LANGUAGE", "ru")
    var name by StringPref("NAME", "")
    var surname by StringPref("SURNAME", "")
    var phone by StringPref("PHONE", "")
    var avatar by StringPref("AVATAR", "")
    var rating by FloatPref("RATING", 0F)
}