package com.badcompany.pitak.ui.passenger_post.offer_a_ride

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.badcompany.core.*
import com.badcompany.domain.domainmodel.DriverOffer
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.domainmodel.Place
import com.badcompany.domain.repository.PassengerPostRepository
import com.badcompany.domain.usecases.CreateDriverPost
import com.badcompany.domain.usecases.GetActiveDriverPost
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import com.badcompany.pitak.util.valueNN
import com.badcompany.pitak.viewobjects.PassengerPostViewObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

class OfferARideViewModel @ViewModelInject constructor(private val repository: PassengerPostRepository,
                                                       private val createDriverPost: CreateDriverPost,
                                                       private val getActiveDriverPost: GetActiveDriverPost) :
    BaseViewModel() {

    val isOffering = MutableLiveData<Boolean>()
    val hasFinished = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    val offeringPostId = MutableLiveData<Long>()


    fun offerARide(postId: Long, myPrice: Int?, message: String, passPost: PassengerPostViewObj) {
        isOffering.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (offeringPostId.value == null) createPost(passPost)
            sendAnOffer(postId, myPrice, message)
        }
    }

    private suspend fun createPost(passPost: PassengerPostViewObj) {
        println("COBUG:  ${Thread.currentThread().name}")
        val placeFrom = Place(
            passPost.from.districtId,
            passPost.from.regionId,
            passPost.from.lat,
            passPost.from.lon,
            passPost.from.regionName,
            passPost.from.name,
        )
        val placeTo = Place(
            passPost.to.districtId,
            passPost.to.regionId,
            passPost.to.lat,
            passPost.to.lon,
            passPost.to.regionName,
            passPost.to.name,
        )
        val driverPost = DriverPost(null, placeFrom, placeTo, passPost.price,
                                    passPost.departureDate,
                                    passPost.finishedDate,
                                    passPost.timeFirstPart,
                                    passPost.timeSecondPart,
                                    passPost.timeThirdPart,
                                    passPost.timeFourthPart,
                                    null,
                                    null,
                                    null,
                                    passPost.seat,
                                    0,
                                    postStatus = EPostStatus.CREATED
        )
        when (val response = createDriverPost.execute(driverPost)) {
            is ErrorWrapper.RespError -> {
                withContext(Dispatchers.Main) {
                    createResponse.value = response
                    errorMessage.value = response.message
                    isOffering.value = false
                }
            }
            is ErrorWrapper.SystemError -> {
                withContext(Dispatchers.Main) {
                    createResponse.value = response
                    errorMessage.value = response.err.localizedMessage
                    isOffering.value = false
                }
            }
            is ResultWrapper.Success -> {
                withContext(Dispatchers.Main) {
                    offeringPostId.value = response.value.id
                }
            }
            else -> {
            }
        }

    }


    private suspend fun sendAnOffer(postId: Long, myPrice: Int?, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            println("COBUG:  ${Thread.currentThread().name}")
            val responseOfferCreate =
                repository.offerARide(DriverOffer(postId,
                                                  myPrice,
                                                  message,
                                                  offeringPostId.valueNN))
            withContext(Dispatchers.Main) {
                when (responseOfferCreate) {
                    is ResponseError -> {
                        errorMessage.value = responseOfferCreate.message
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

    val createResponse = SingleLiveEvent<ResultWrapper<DriverPost>>()
    val activePostsResponse = SingleLiveEvent<ResultWrapper<List<DriverPost>>>()

    @ExperimentalSplittiesApi
    fun getActivePosts() {
        activePostsResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = getActiveDriverPost.execute()

            withContext(Dispatchers.Main) {
                activePostsResponse.value = response
            }
        }
    }

    fun setOfferingPost(id: Long?) {
        offeringPostId.value = id
    }
}