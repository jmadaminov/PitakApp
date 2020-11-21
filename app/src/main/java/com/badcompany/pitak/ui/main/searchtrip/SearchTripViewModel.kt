package com.badcompany.pitak.ui.main.searchtrip

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Filter
import com.badcompany.domain.domainmodel.PassengerPost
import com.badcompany.domain.domainmodel.Place
import com.badcompany.domain.usecases.GetPassengerPostWithFilter
import com.badcompany.domain.usecases.GetPlacesFeed
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.AppPreferences
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

class SearchTripViewModel  @ViewModelInject constructor(private val getPassengerPostWithFilter: GetPassengerPostWithFilter,
                                                        private val getPlacesFeed: GetPlacesFeed) :
    BaseViewModel() {


    val passengerPostsReponse = SingleLiveEvent<ResultWrapper<List<PassengerPost>>>()
    var currentPage = 0
    var filter = Filter()

    @ExperimentalSplittiesApi
    fun getPassengerPost() {
        passengerPostsReponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = getPassengerPostWithFilter.execute(                 filter)

            withContext(Dispatchers.Main) {
                passengerPostsReponse.value = response
            }

        }
    }


//    var placeFrom: Place? = null
//    var placeTo: Place? = null

    private var fromFeedJob: Job? = null
    private var toFeedJob: Job? = null
    val fromPlacesResponse = SingleLiveEvent<ResultWrapper<List<Place>>>()
    val toPlacesResponse = SingleLiveEvent<ResultWrapper<List<Place>>>()


    @ExperimentalSplittiesApi
    fun getPlacesFeed(queryString: String, isFrom: Boolean = true) {
        if (isFrom) fromPlacesResponse.value = ResultWrapper.InProgress
        else toPlacesResponse.value = ResultWrapper.InProgress
        resetFromFeedJob(isFrom)
        viewModelScope.launch(Dispatchers.IO + if (isFrom) fromFeedJob!! else toFeedJob!!) {
            val response =                getPlacesFeed.execute(queryString)

            withContext(Dispatchers.Main) {
                if (isFrom) fromPlacesResponse.value = response
                else toPlacesResponse.value = response
            }
        }
    }

    private fun resetFromFeedJob(isFrom: Boolean) {
        fromFeedJob?.cancel()
        fromFeedJob = Job()
        toFeedJob?.cancel()
        toFeedJob = Job()

    }

    override fun onCleared() {
        super.onCleared()
        fromFeedJob?.cancel()
        toFeedJob?.cancel()
    }

    fun resetFilter() {
        filter = Filter()
    }


}