package com.novatec.pitak.ui.main.mytrips

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.novatec.pitak.ui.BaseViewModel
import javax.inject.Inject

class MyTripsViewModel  @ViewModelInject constructor() : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}