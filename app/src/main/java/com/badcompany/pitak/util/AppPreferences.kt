package com.badcompany.pitak.util

/**
 * Created by jahon on 23-Apr-20
 */


import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.Preferences

@ExperimentalSplittiesApi object AppPreferences : Preferences("myPrefs") {
//    @ExperimentalSplittiesApi
//    var isUserLoggedIn by boolPref(false)
    @ExperimentalSplittiesApi
    var token by stringPref("")
    @ExperimentalSplittiesApi
    var language by stringPref("uz")
    @ExperimentalSplittiesApi
    var name by stringPref("")
    @ExperimentalSplittiesApi
    var surname by stringPref("")
    @ExperimentalSplittiesApi
    var phone by stringPref("")
}