package com.novatec.epitak.ui.driver_post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.core.ResponseError
import com.novatec.core.ResponseSuccess
import com.novatec.core.ResultWrapper
import com.novatec.core.exhaustive
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.domainmodel.Offer
import com.novatec.domain.repository.DriverPostRepository
import com.novatec.domain.usecases.DeleteDriverPost
import com.novatec.domain.usecases.FinishDriverPost
import com.novatec.epitak.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

@HiltViewModel
class DriverPostViewModel @Inject constructor(val postRepository: DriverPostRepository,
                                              val deletePost: DeleteDriverPost,
                                              val finishPost: FinishDriverPost) :
    BaseViewModel() {

    val postData = MutableLiveData<DriverPost?>()
    val errorMessage = MutableLiveData<String?>()
    val isLoading = MutableLiveData<Boolean>()
    val offerActionLoading = MutableLiveData<Boolean>()

    fun getPostById(id: Long) {
        isLoading.value = true
        viewModelScope.launch(IO) {
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

    val deletePostReponse = MutableLiveData<ResultWrapper<String>>()
    val passengerOffers = MutableLiveData<ResultWrapper<List<Offer>>>()
    val parcelOffers = MutableLiveData<ResultWrapper<List<Offer>>>()
    val finishPostResponse = MutableLiveData<ResultWrapper<String>>()

    @ExperimentalSplittiesApi
    fun deletePost(identifier: String) {
        isLoading.value = true
        deletePostReponse.value = ResultWrapper.InProgress
        viewModelScope.launch(IO) {
            val response = deletePost.execute(identifier)

            withContext(Dispatchers.Main) {
                isLoading.value = false
                deletePostReponse.value = response
            }
        }
    }


    @ExperimentalSplittiesApi
    fun deletePassenger(passengerId: Long, postId: Long) {
        isLoading.value = true
        viewModelScope.launch(IO) {
            val response = postRepository.removePassengerFromPost(postId, passengerId)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResponseError -> {
                        errorMessage.value = response.message
                    }
                    is ResponseSuccess -> {
                        if (response.value != null) postData.value =
                            response.value else getPostById(postId)
//                        getOffersForPost(postId)
                        getParcelOffersByPostId(postId)
                        getPassengerOffersByPostId(postId)
                    }
                }
            }
        }
    }

    @ExperimentalSplittiesApi
    fun deleteParcel(parcelId: Long, postId: Long) {
        isLoading.value = true
        viewModelScope.launch(IO) {
            val response = postRepository.removeParcelFromPost(postId, parcelId)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResponseError -> {
                        errorMessage.value = response.message
                    }
                    is ResponseSuccess -> {
                        if (response.value != null) postData.value =
                            response.value else getPostById(postId)
//                        getOffersForPost(postId)
                        getParcelOffersByPostId(postId)
                        getPassengerOffersByPostId(postId)
                    }
                }
            }
        }
    }

    @ExperimentalSplittiesApi
    fun finishPost(identifier: String) {
        isLoading.value = true
        finishPostResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(IO) {
            val response = finishPost.execute(identifier)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                finishPostResponse.value = response
            }
        }
    }

    val offerActionResp = MutableLiveData<String?>()
    val offerActionError = MutableLiveData<String?>()
    fun acceptOffer(id: Long) {
        offerActionLoading.value = true
        viewModelScope.launch(IO) {
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
        viewModelScope.launch(IO) {
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

    val startedTripResp = MutableLiveData<Boolean>()

    fun startTrip(id: Long) {
        isLoading.value = true
        viewModelScope.launch(IO) {
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


    fun getPassengerOffersByPostId(postId: Long) {
        passengerOffers.value = ResultWrapper.InProgress
        viewModelScope.launch(IO) {
            val response = postRepository.getPassengerOffers(postId)
            passengerOffers.postValue(response)
        }
    }

    fun getParcelOffersByPostId(postId: Long) {
        parcelOffers.value = ResultWrapper.InProgress
        viewModelScope.launch(IO) {
            val response = postRepository.getParcelOffers(postId)
            parcelOffers.postValue(response)
        }

    }

}
