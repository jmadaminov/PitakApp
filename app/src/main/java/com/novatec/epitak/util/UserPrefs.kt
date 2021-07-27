package com.novatec.epitak.util

/**
 * Created by jahon on 23-Apr-20
 */


import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.Preferences

@ExperimentalSplittiesApi object UserPrefs : Preferences("myPrefs") {

    var userId by stringPref("userId", "")
    var token by stringPref("token","")
    var name by stringPref("name","")
    var defaultCarId by stringOrNullPref("defaultCarId", null)
    var surname by stringPref("surname","")
    var phone by stringPref("phone","")
    var avatar by stringPref("avatar","")
    var rating by floatPref("rating",0F)
}