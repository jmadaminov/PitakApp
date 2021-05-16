package com.novatec.epitak.ui.auth.login

import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.novatec.core.ResponseWrapper
import com.novatec.core.numericOnly
import com.novatec.domain.domainmodel.UserCredentials
import com.novatec.domain.usecases.LogUserIn
import com.novatec.epitak.ui.BaseViewModel
import com.novatec.epitak.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@HiltViewModel
class LoginViewModel @Inject constructor(private val logUserIn: LogUserIn) :
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
