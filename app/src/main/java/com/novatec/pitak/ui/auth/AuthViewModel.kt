package com.novatec.pitak.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject

import com.novatec.pitak.R
import com.novatec.pitak.ui.BaseViewModel
import javax.inject.Inject

class AuthViewModel  @ViewModelInject constructor() : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}