package com.badcompany.pitak.ui.main.searchtrip

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.MAX_PRICE
import com.badcompany.domain.domainmodel.MIN_PRICE
import com.badcompany.domain.domainmodel.Place
import com.badcompany.domain.usecases.GetPlacesFeed
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import com.badcompany.pitak.util.valueNN
import com.badcompany.remote.model.FilterModel
import com.badcompany.remote.model.PassengerPostModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

class SearchTripViewModel @ViewModelInject constructor(val postFilterRepository: PostFilterRepository,
                                                       private val getPlacesFeed: GetPlacesFeed) :
    BaseViewModel() {

    private val _filter = MutableLiveData(FilterModel())
    val filter: LiveData<FilterModel> get() = _filter
    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> get() = _count

    var postOffers: LiveData<PagingData<PassengerPostModel>> = MutableLiveData()
    fun getPassengerPost() {
        postOffers = postFilterRepository.getFilteredPosts(_filter.valueNN).cachedIn(viewModelScope)
    }

    private var fromFeedJob: Job? = null
    private var toFeedJob: Job? = null
    val fromPlacesResponse = SingleLiveEvent<ResultWrapper<List<Place>>>()
    val toPlacesResponse = SingleLiveEvent<ResultWrapper<List<Place>>>()


    @ExperimentalSplittiesApi
    fun getPlacesFeed(queryString: String, isFrom: Boolean = true) {
        if (isFrom) fromPlacesResponse.value = ResultWrapper.InProgress
        else toPlacesResponse.value = ResultWrapper.InProgress
        resetFeedJobs()
        viewModelScope.launch(Dispatchers.IO + if (isFrom) fromFeedJob!! else toFeedJob!!) {
            val response = getPlacesFeed.execute(queryString)
            withContext(Dispatchers.Main) {
                if (isFrom) fromPlacesResponse.value = response
                else toPlacesResponse.value = response
            }
        }
    }

    private fun resetFeedJobs() {
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
        _filter.value = FilterModel()
    }


    fun applyFilter() {
        _filter.postValue(_filter.value)
        checkAppliedFiltersCount()
    }

    fun filterAC(isACChecked: Boolean) {
        if (isACChecked) _filter.valueNN.airConditioner = true
        else _filter.valueNN.airConditioner = null
    }

    fun setFilterPrices(minPrice: Int?, maxPrice: Int?) {
        _filter.valueNN.minPrice = minPrice
        _filter.valueNN.maxPrice = maxPrice
    }

    fun placeFromSelected(place: Place) {
        _filter.valueNN.fromRegionId = place.regionId
        _filter.valueNN.fromDistrictId = place.districtId
        applyFilter()
    }

    fun placeToSelected(place: Place) {
        _filter.valueNN.toRegionId = place.regionId
        _filter.valueNN.toDistrictId = place.districtId
        applyFilter()
    }

    fun setDate(dayOfMonth: Int, month: Int, year: Int) {
        _filter.valueNN.departureDate = "$dayOfMonth.$month.$year"
        applyFilter()
    }

    fun dayTimePartsChecked(timeFirstPart: Boolean,
                            timeSecondPart: Boolean,
                            timeThirdPart: Boolean,
                            timeFourthPart: Boolean) {

        _filter.valueNN.timeFirstPart = timeFirstPart
        _filter.valueNN.timeSecondPart = timeSecondPart
        _filter.valueNN.timeThirdPart = timeThirdPart
        _filter.valueNN.timeFourthPart = timeFourthPart
    }

    fun seatCountChanged(count: Int) {
        _filter.valueNN.seat = count
    }


    private fun checkAppliedFiltersCount() {
        var appliedFilterCount = 0
        if (_filter.valueNN.minPrice != null && _filter.valueNN.maxPrice != null && (_filter.valueNN.minPrice != MIN_PRICE || _filter.valueNN.maxPrice != MAX_PRICE)) {
            appliedFilterCount++
        }
        if (_filter.valueNN.airConditioner == true) appliedFilterCount++
        if (_filter.valueNN.seat != 1) appliedFilterCount++
        if (_filter.valueNN.timeFirstPart == true || _filter.valueNN.timeSecondPart == true || _filter.valueNN.timeThirdPart == true || _filter.valueNN.timeFourthPart == true) appliedFilterCount++
        _count.value = appliedFilterCount
    }


}