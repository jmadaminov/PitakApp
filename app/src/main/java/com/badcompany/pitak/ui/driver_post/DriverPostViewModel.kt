package com.badcompany.pitak.ui.driver_post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.badcompany.core.ResponseError
import com.badcompany.core.ResponseSuccess
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.repository.DriverPostRepository
import com.badcompany.domain.usecases.DeleteDriverPost
import com.badcompany.domain.usecases.FinishDriverPost
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import com.badcompany.remote.model.OfferDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

class DriverPostViewModel @ViewModelInject constructor(val postRepository: DriverPostRepository,
                                                       val postOffersRepository: PostOffersRepository,
                                                       val deletePost: DeleteDriverPost,
                                                       val finishPost: FinishDriverPost) :
    BaseViewModel() {

    val postData = SingleLiveEvent<DriverPost>()
    val errorMessage = SingleLiveEvent<String>()
    val isLoading = SingleLiveEvent<Boolean>()
    val offerActionLoading = SingleLiveEvent<Boolean>()

    fun getPostById(id: Long) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = postRepository.getDriverPostById(id)
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


    lateinit var postOffers: LiveData<PagingData<OfferDTO>>
    fun getOffersForPost(id: Long) {
        postOffers = postOffersRepository.getOffersForPost(id).cachedIn(viewModelScope)
    }

    val deletePostReponse = SingleLiveEvent<ResultWrapper<String>>()
    val finishPostResponse = SingleLiveEvent<ResultWrapper<String>>()

    @ExperimentalSplittiesApi
    fun deletePost(identifier: String) {
        isLoading.value = true
        deletePostReponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = deletePost.execute(identifier)

            withContext(Dispatchers.Main) {
                isLoading.value = false
                deletePostReponse.value = response
            }
        }
    }

    @ExperimentalSplittiesApi
    fun finishPost(identifier: String) {
        isLoading.value = true
        finishPostResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = finishPost.execute(identifier)

            withContext(Dispatchers.Main) {
                isLoading.value = false
                finishPostResponse.value = response
            }
        }
    }

    val offerActionResp = SingleLiveEvent<String>()
    val offerActionError = SingleLiveEvent<String>()
    fun acceptOffer(id: Long) {

        offerActionLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = postRepository.acceptOffer(id)

            withContext(Dispatchers.Main) {
                offerActionLoading.value = false
                when (response) {
                    is ResponseError -> offerActionError.value = response.message
                    is ResponseSuccess -> {
                        offerActionError.value = null
                        offerActionResp.value = response.value
                    }
                }.exhaustive
            }
        }
    }

    fun cancelOffer(id: Long) {

        offerActionLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = postRepository.rejectOffer(id)
            withContext(Dispatchers.Main) {
                offerActionLoading.value = false
                when (response) {
                    is ResponseError -> offerActionError.value = response.message
                    is ResponseSuccess -> {
                        offerActionError.value = null
                        offerActionResp.value = response.value
                    }
                }.exhaustive
            }
        }
    }

    val startedTripResp = SingleLiveEvent<Boolean>()

    fun startTrip(id: Long) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = postRepository.startTrip(id)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResponseError -> {
                        errorMessage.value = response.message
                    }
                    is ResponseSuccess -> {
                        startedTripResp.postValue(true)
                    }
                }.exhaustive

            }
        }

    }

}
