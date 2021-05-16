package com.novatec.epitak.ui.auth.register

import androidx.lifecycle.viewModelScope
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.User
import com.novatec.domain.usecases.RegisterUser
import com.novatec.epitak.ui.BaseViewModel
import com.novatec.epitak.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
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
