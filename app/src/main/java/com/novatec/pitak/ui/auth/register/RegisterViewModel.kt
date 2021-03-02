package com.novatec.pitak.ui.auth.register

import android.util.Log
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.core.exhaustive
import com.novatec.core.numericOnly
import com.novatec.domain.domainmodel.User
import com.novatec.domain.usecases.RegisterUser
import com.novatec.pitak.R
import com.novatec.pitak.ui.BaseViewModel
import com.novatec.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterViewModel  @ViewModelInject constructor(private val registerUser: RegisterUser) :
    BaseViewModel() {

    private val _registerForm = SingleLiveEvent<RegisterFormState>()
    val registerFormState: SingleLiveEvent<RegisterFormState> = _registerForm

    val regResp = SingleLiveEvent<ResultWrapper<String>>()

    fun register(user: User) {
        regResp.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = registerUser.execute(user)
            withContext(Dispatchers.Main) {
                regResp.value = response
            }
        }
    }




}
