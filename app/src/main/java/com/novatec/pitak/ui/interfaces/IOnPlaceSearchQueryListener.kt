package com.novatec.pitak.ui.interfaces

interface IOnPlaceSearchQueryListener {
    fun onQuery(query: CharSequence?, isFrom: Boolean = true, isSelectedFromFeed:Boolean=false)
}