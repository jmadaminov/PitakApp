package com.novatec.pitak.ui.auth.confirm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.core.ResponseWrapper
import com.novatec.core.ResultWrapper
import com.novatec.core.numericOnly
import com.novatec.domain.domainmodel.AuthBody
import com.novatec.domain.domainmodel.UserCredentials
import com.novatec.domain.usecases.LogUserIn
import com.novatec.domain.usecases.SmsConfirm
import com.novatec.pitak.ui.BaseViewModel
import com.novatec.pitak.util.SingleLiveEvent
import com.novatec.pitak.util.launchPeriodicAsync
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main


class PhoneConfirmViewModel @ViewModelInject constructor(private val smsConfirm: SmsConfirm,
                                                         private val logUserIn: LogUserIn) :
    BaseViewModel() {

    companion object {
        const val REGAIN_CODE_INTERVAL = 60000L
    }

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

    private val _requestSmsCountDown = MutableLiveData<Int>()
    val requestSmsCountDown: LiveData<Int> get() = _requestSmsCountDown

    var counterJob = Job()
    fun startTimer() {
        CoroutineScope(Dispatchers.IO + counterJob).launchPeriodicAsync(1000,
                                                                        REGAIN_CODE_INTERVAL) { timeLeft ->
            CoroutineScope(Main).launch {
                _requestSmsCountDown.value = (timeLeft / 1000).toInt()
            }
            if (timeLeft == 0L) {
                resetTimer()
            }
        }
    }

    fun resetTimer() {
        counterJob.cancel()
        counterJob = Job()
    }


    private var _respRegainCodeSuccess = MutableLiveData<ResponseWrapper<UserCredentials?>>()
    val respRegainCode: LiveData<ResponseWrapper<UserCredentials?>> get() = _respRegainCodeSuccess

    fun requestCodeAgain(phoneNum: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = logUserIn.execute(phoneNum.numericOnly())
            withContext(Main) {
                _respRegainCodeSuccess.value = response
            }
        }
    }

}
