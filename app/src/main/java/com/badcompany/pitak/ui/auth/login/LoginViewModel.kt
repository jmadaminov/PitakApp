package com.badcompany.pitak.ui.auth.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.core.numericOnly
import com.badcompany.domain.usecases.LogUserIn
import com.badcompany.pitak.App
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel @ViewModelInject constructor(private val logUserIn: LogUserIn) :
    BaseViewModel() {

    val loginResponse = SingleLiveEvent<ResultWrapper<String>>()
//    private val _loginForm = SingleLiveEvent<LoginFormState>()
//    val loginFormState: SingleLiveEvent<LoginFormState> = _loginForm
//
//    private val _loginResult = MutableLiveData<LoginResult>()
//    val loginResult: LiveData<LoginResult> = _loginResult

    var phoneNum = ""
    fun login(phoneNum: String) {
        this.phoneNum = phoneNum
        loginResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = logUserIn.execute(hashMapOf(Pair(Constants.TXT_PHONE_NUMBER,
                                                            phoneNum.numericOnly()),
                                                       Pair(Constants.TXT_UDID, App.uuid)))
            withContext(Main) {
                loginResponse.value = response
            }
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
