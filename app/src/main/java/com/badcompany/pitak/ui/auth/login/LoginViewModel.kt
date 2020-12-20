package com.badcompany.pitak.ui.auth.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ResponseWrapper
import com.badcompany.core.numericOnly
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.domain.usecases.LogUserIn
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel @ViewModelInject constructor(private val logUserIn: LogUserIn) :
    BaseViewModel() {

    val loginResponse = SingleLiveEvent<ResponseWrapper<UserCredentials?>>()
    val isLoading = SingleLiveEvent<Boolean>()

    var phoneNum = ""
    fun login(phoneNum: String) {
        this.phoneNum = phoneNum
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = logUserIn.execute(phoneNum.numericOnly())
            withContext(Main) {
                loginResponse.value = response
            }
        }
    }


}
