package com.novatec.epitak.util

/**
 * Created by jahon on 22-Jul-20
 */
fun buildAutoCompleteManager(lambda: AutoCompleteManager.Builder.() -> Unit) =
    AutoCompleteManager.Builder().apply(lambda).create()