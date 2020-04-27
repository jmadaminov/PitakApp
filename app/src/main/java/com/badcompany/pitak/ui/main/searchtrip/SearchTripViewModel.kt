package com.badcompany.pitak.ui.main.searchtrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.badcompany.pitak.ui.BaseViewModel
import javax.inject.Inject

class SearchTripViewModel @Inject constructor(): BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}