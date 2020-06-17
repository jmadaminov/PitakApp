package com.badcompany.pitak.ui.addpost.choosedestinations

import android.util.Log
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.AuthBody
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import javax.inject.Inject


class ChooseDestinationsViewModel @Inject constructor(/*private val smsConfirm: SmsConfirm*/) :
    BaseViewModel() {

    val confirmResponse = SingleLiveEvent<ResultWrapper<AuthBody>>()

    fun confirm(phone: String, code: String) {
//        confirmResponse.value = ResultWrapper.InProgress
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = smsConfirm.execute(UserCredentials(phone.numericOnly(), code))
//            withContext(Main) {
//                confirmResponse.value = response
//            }
//        }

    }

    // A placeholder username validation check
    private fun isCodeValid(code: String): Boolean {
        return code.length == 5
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    fun makeFromSearch(text: String) {
        Log.wtf(TAG, "makeFromSearch: $text")

    }
}
