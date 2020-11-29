package com.badcompany.pitak.ui.auth.confirm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ResultWrapper
import com.badcompany.core.numericOnly
import com.badcompany.domain.domainmodel.AuthBody
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.domain.usecases.SmsConfirm
import com.badcompany.pitak.App
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PhoneConfirmViewModel  @ViewModelInject constructor(private val smsConfirm: SmsConfirm) :
    BaseViewModel() {
    val confirmResponse = SingleLiveEvent<ResultWrapper<AuthBody>>()

    fun confirm(userCredentials: UserCredentials) {
        confirmResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = smsConfirm.execute(userCredentials)
            withContext(Main) {
                confirmResponse.value = response
            }
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
