package com.badcompany.pitak.ui.passenger_post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ResponseError
import com.badcompany.core.ResponseSuccess
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.DriverOffer
import com.badcompany.domain.domainmodel.PassengerPost
import com.badcompany.domain.repository.PassengerPostRepository
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PassengerPostViewModel @ViewModelInject constructor(private val repository: PassengerPostRepository) :
    BaseViewModel() {


    val postData = SingleLiveEvent<PassengerPost>()
    val errorMessage = SingleLiveEvent<String>()
    val isLoading = SingleLiveEvent<Boolean>()
    fun getPostById(id: Long) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPassengerPostById(id)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResponseError -> errorMessage.value = response.message
                    is ResponseSuccess -> {
                        errorMessage.value = null
                        postData.value = response.value
                    }
                }.exhaustive
            }
        }
    }

}