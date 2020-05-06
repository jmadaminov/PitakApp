package com.badcompany.pitak.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ResultWrapper
import com.badcompany.core.numericOnly
import com.badcompany.domain.usecases.LogUserIn
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject


class LoginViewModel @Inject constructor(private val logUserIn: LogUserIn) : BaseViewModel() {

    val response = SingleLiveEvent<ResultWrapper<String>>()
    private val _loginForm = SingleLiveEvent<LoginFormState>()
    val loginFormState: SingleLiveEvent<LoginFormState> = _loginForm
//
//    private val _loginResult = MutableLiveData<LoginResult>()
//    val loginResult: LiveData<LoginResult> = _loginResult

    var phoneNum=""
    fun login(phoneNum: String) {
            this.phoneNum = phoneNum
            response.value = ResultWrapper.InProgress
            viewModelScope.launch {
                response.value = logUserIn.execute(phoneNum.numericOnly())
            }
    }

//    // A placeholder username validation check
//    private fun isPhoneValid(phoneNum: String): Boolean {
//        return if (phoneNum.numericOnly().length == 12) true else {
//            _loginForm.value = LoginFormState(phoneError = R.string.incorrect_phone_number_format)
//            false
//        }
//    }

//    // A placeholder password validation check
//    private fun isPasswordValid(password: String): Boolean {
//        return password.length > 5
//    }
}
