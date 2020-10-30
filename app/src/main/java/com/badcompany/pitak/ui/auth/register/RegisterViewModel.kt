package com.badcompany.pitak.ui.auth.register

import androidx.lifecycle.viewModelScope
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.User
import com.badcompany.domain.usecases.RegisterUser
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val registerUser: RegisterUser) :
    BaseViewModel() {

    private val _registerForm = SingleLiveEvent<RegisterFormState>()
    val registerFormState: SingleLiveEvent<RegisterFormState> = _registerForm

    val regResp = SingleLiveEvent<ResultWrapper<String>>()

    fun register(user: User) {
        regResp.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = registerUser.execute(user)
            withContext(Dispatchers.Main) {
                regResp.value = response
            }
        }
    }


}
