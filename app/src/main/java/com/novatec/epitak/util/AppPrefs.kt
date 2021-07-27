package com.novatec.epitak.util

/**
 * Created by jahon on 23-Apr-20
 */


import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.Preferences

@ExperimentalSplittiesApi object AppPrefs : Preferences("appPrefs") {
    var language by stringPref("language", "ru")
    var isFirstTime by boolPref("isFirstTime", true)
    var hasSeenTutorial by boolPref("hasSeenTutorial", false)
}