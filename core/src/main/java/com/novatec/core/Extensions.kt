package com.novatec.core

/**
 * Created by jahon on 13-Apr-20
 */

val <T> T.exhaustive: T
    get() = this

fun String.numericOnly() : String {
    return Regex("[^0-9]").replace(this, "")
}