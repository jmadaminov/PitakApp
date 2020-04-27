package com.badcompany.pitak.ui.auth.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.core.numericOnly
import com.badcompany.domain.usecases.LogUserIn
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class LoginViewModel @Inject constructor(private val logUserIn: LogUserIn) : BaseViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(phoneNum: String) {
        // can be launched in a separate asynchronous job
        /* val result = loginRepository.login(username, password)

         if (result is ResultWrapper.Success) {
             _loginResult.value =
                 LoginResult(success = LoggedInUserView(displayName = result.value.displayName))
         } else {
             _loginResult.value = LoginResult(error = R.string.login_failed)
         }*/

        viewModelScope.launch {
            val response = logUserIn.execute(phoneNum)
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    Log.d("DEBUG REGISTER", "ResponseError" + response.toString())
                }
                is ErrorWrapper.SystemError -> {
                    Log.d("DEBUG REGISTER", "SystemError" + response.toString())
                }
                is ResultWrapper.Success -> {
                    Log.d("DEBUG REGISTER", "Success" + response.toString())
                }
                ResultWrapper.InProgress -> {

                }
            }.exhaustive
        }

    }

    fun loginDataChanged(phone: String/*, password: String*/) {
        if (!isPhoneValid(phone)) {
            _loginForm.value =
                LoginFormState(usernameError = R.string.invalid_username)
        } /*else if (!isPasswordValid(password)) {
            _loginForm.value =
                LoginFormState(passwordError = R.string.invalid_password)
        } */ else {
            _loginForm.value =
                LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isPhoneValid(phoneNum: String): Boolean {
        return if (phoneNum.numericOnly().length==11) {
            Patterns.PHONE.matcher(phoneNum).matches()
        } else {
            phoneNum.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
