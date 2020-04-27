package com.badcompany.pitak.ui.auth.confirm

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.usecases.LogUserIn
import com.badcompany.pitak.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class PhoneConfirmViewModel @Inject constructor(private val logUserIn: LogUserIn) :
    BaseViewModel() {

    fun confirm(code: String) {
        viewModelScope.launch {
            val response = logUserIn.execute(code)
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

    // A placeholder username validation check
    private fun isCodeValid(code: String): Boolean {
        return code.length == 5
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
