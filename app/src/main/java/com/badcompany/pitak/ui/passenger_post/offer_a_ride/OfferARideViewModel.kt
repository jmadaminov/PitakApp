package com.badcompany.pitak.ui.passenger_post.offer_a_ride

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ResponseError
import com.badcompany.core.ResponseSuccess
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.DriverOffer
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.domainmodel.Place
import com.badcompany.domain.repository.PassengerPostRepository
import com.badcompany.domain.usecases.CreateDriverPost
import com.badcompany.domain.usecases.GetActiveDriverPost
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import com.badcompany.pitak.viewobjects.PassengerPostViewObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

class OfferARideViewModel @ViewModelInject constructor(private val repository: PassengerPostRepository,
                                                       private val createDriverPost: CreateDriverPost,
                                                       val getActiveDriverPost: GetActiveDriverPost) :
    BaseViewModel() {

    val isOffering = MutableLiveData<Boolean>()
    val hasFinished = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    val offeringPostId = MutableLiveData<Int>()


    fun offerARide(postId: Long, myPrice: Int?, message: String, passPost:PassengerPostViewObj) {
        isOffering.value = true

        if (offeringPostId == null) {
            viewModelScope.launch(Dispatchers.IO) {

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
                passPost.car
                )
                val response = createDriverPost.execute()

                withContext(Dispatchers.Main) {
                    createResponse.value = response
                }
            }
        }

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


    val createResponse = SingleLiveEvent<ResultWrapper<String>>()

    @ExperimentalSplittiesApi
    fun createDriverPost(driverPost: DriverPost) {
        createResponse.value = ResultWrapper.InProgress


        viewModelScope.launch(Dispatchers.IO) {
            val response = createDriverPost.execute(driverPost)

            withContext(Dispatchers.Main) {
                createResponse.value = response
            }
        }
    }

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
}