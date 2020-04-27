package com.badcompany.core

/**
 * Created by jahon on 13-Apr-20
 */

val <T> T.exhaustive: T
    get() = this


fun String.numericOnly() : String {
    return  this.replace("[^0-9.]", "")
}