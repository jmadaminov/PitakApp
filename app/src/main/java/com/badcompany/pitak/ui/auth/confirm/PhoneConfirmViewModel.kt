package com.badcompany.pitak.ui.auth.confirm

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.core.numericOnly
import com.badcompany.domain.domainmodel.AuthBody
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.domain.usecases.SmsConfirm
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class PhoneConfirmViewModel @Inject constructor(private val smsConfirm: SmsConfirm) :
    BaseViewModel() {
    val response = SingleLiveEvent<ResultWrapper<AuthBody>>()

    fun confirm(phone: String, code: String) {
        response.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO)  {

                response.value = smsConfirm.execute(UserCredentials(phone.numericOnly(), code))

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
