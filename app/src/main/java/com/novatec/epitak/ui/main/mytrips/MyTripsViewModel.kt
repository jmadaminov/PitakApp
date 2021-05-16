package com.novatec.epitak.ui.main.mytrips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.novatec.epitak.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyTripsViewModel @Inject constructor() : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}