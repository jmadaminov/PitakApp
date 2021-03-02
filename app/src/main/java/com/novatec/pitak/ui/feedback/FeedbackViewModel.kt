package com.novatec.pitak.ui.feedback

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novatec.core.ResponseError
import com.novatec.core.ResponseSuccess
import com.novatec.core.exhaustive
import com.novatec.domain.repository.UserRepository
import com.novatec.pitak.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedbackViewModel  @ViewModelInject constructor(val userRepository: UserRepository) : BaseViewModel() {


    val feedbackResponse = MutableLiveData<String>()
    val errorString = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun sendFeedback(feedback: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val resp = userRepository.sendFeedback(feedback)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (resp) {
                    is ResponseError -> {
                        errorString.value = resp.message
                    }
                    is ResponseSuccess -> {
                        feedbackResponse.value = ""
                    }
                }.exhaustive
            }
        }
    }

}
