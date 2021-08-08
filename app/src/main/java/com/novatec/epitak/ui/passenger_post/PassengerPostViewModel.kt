package com.novatec.epitak.ui.passenger_post

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.core.ResponseError
import com.novatec.core.ResponseSuccess
import com.novatec.core.exhaustive
import com.novatec.domain.domainmodel.DriverOffer
import com.novatec.domain.domainmodel.PassengerPost
import com.novatec.domain.repository.PassengerPostRepository
import com.novatec.epitak.ui.BaseViewModel
import com.novatec.epitak.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class PassengerPostViewModel @Inject constructor(private val repository: PassengerPostRepository) :
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
//                        errorMessage.value = null
                        postData.value = response.value!!
                    }
                }.exhaustive
            }
        }
    }

}