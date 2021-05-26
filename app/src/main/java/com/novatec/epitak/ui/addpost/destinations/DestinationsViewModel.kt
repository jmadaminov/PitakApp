package com.novatec.epitak.ui.addpost.destinations

import androidx.lifecycle.viewModelScope
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.Place
import com.novatec.domain.usecases.GetPlacesFeed
import com.novatec.epitak.ui.BaseViewModel
import com.novatec.epitak.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject


@HiltViewModel
class DestinationsViewModel @Inject constructor(private val getPlacesFeed: GetPlacesFeed) :
    BaseViewModel() {

    var placeFrom: Place? = null
    var placeTo: Place? = null

    private var fromFeedJob: Job? = null
    private var toFeedJob: Job? = null
    val placesResponse = SingleLiveEvent<ResultWrapper<List<Place>>>()

    var preSelectedPlace: Place? = null

    @ExperimentalSplittiesApi
    fun getPlacesFeed(queryString: String, isFrom: Boolean = true) {
        placesResponse.value = ResultWrapper.InProgress
        resetFromFeedJob()
        viewModelScope.launch(Dispatchers.IO + if (isFrom) fromFeedJob!! else toFeedJob!!) {
            placesResponse.postValue(getPlacesFeed.execute(queryString))
        }
    }

    private fun resetFromFeedJob() {
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


}
