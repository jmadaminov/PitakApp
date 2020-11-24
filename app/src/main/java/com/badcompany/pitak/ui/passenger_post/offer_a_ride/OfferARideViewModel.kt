package com.badcompany.pitak.ui.passenger_post.offer_a_ride

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ResponseError
import com.badcompany.core.ResponseSuccess
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.DriverOffer
import com.badcompany.domain.repository.PassengerPostRepository
import com.badcompany.pitak.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfferARideViewModel @ViewModelInject constructor(private val repository: PassengerPostRepository) :
    BaseViewModel() {

    val isOffering = MutableLiveData<Boolean>()
    val hasFinished = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    fun offerARide(postId: Long, myPrice: Int?, message: String) {
        isOffering.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.offerARide(DriverOffer(postId, myPrice, message))
            withContext(Dispatchers.Main) {
                when (response) {
                    is ResponseError -> {
                        errorMessage.value = response.message
                        isOffering.value = false
                    }
                    is ResponseSuccess -> {
                        hasFinished.value = true
                        isOffering.value = false
                    }
                }.exhaustive
            }
        }

    }


}