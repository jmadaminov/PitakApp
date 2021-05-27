package com.novatec.epitak.ui.main.searchtrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.Filter
import com.novatec.domain.domainmodel.Filter.Companion.MAX_PRICE
import com.novatec.domain.domainmodel.Filter.Companion.MIN_PRICE
import com.novatec.domain.domainmodel.Place
import com.novatec.domain.usecases.GetPlacesFeed
import com.novatec.epitak.ui.BaseViewModel
import com.novatec.remote.model.PassengerPostModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

@HiltViewModel
class SearchTripViewModel @Inject constructor(val postFilterRepository: PostFilterRepository,
                                              private val getPlacesFeed: GetPlacesFeed) :
    BaseViewModel() {

    private val _filter = MutableLiveData(Filter())
    val filter: LiveData<Filter> get() = _filter
    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> get() = _count

    var postOffers: LiveData<PagingData<PassengerPostModel>> = MutableLiveData()
    fun getPassengerPost() {
        postOffers = postFilterRepository.getFilteredPosts(_filter).cachedIn(viewModelScope)
    }

    private var fromFeedJob: Job? = null
    private var toFeedJob: Job? = null
    val fromPlacesResponse = MutableLiveData<ResultWrapper<List<Place>>>()
    val toPlacesResponse = MutableLiveData<ResultWrapper<List<Place>>>()


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
        _filter.value = Filter()
    }


    fun applyFilter() {
        _filter.postValue(_filter.value)
        checkAppliedFiltersCount()
    }

    fun filterAC(isACChecked: Boolean) {
        if (isACChecked) _filter.value!!.airConditioner = true
        else _filter.value!!.airConditioner = null
    }

    fun setFilterPrices(minPrice: Int?, maxPrice: Int?) {
        _filter.value!!.minPrice = minPrice
        _filter.value!!.maxPrice = maxPrice
    }

    fun placeFromSelected(place: Place) {
        _filter.value!!.fromRegionId = place.regionId
        _filter.value!!.fromDistrictId = place.districtId
        applyFilter()
    }

    fun clearPlaceFromSelection() {
        _filter.value!!.fromRegionId = null
        _filter.value!!.fromDistrictId = null
        applyFilter()
    }

    fun clearPlaceToSelection() {
        _filter.value!!.toRegionId = null
        _filter.value!!.toDistrictId = null
        applyFilter()
    }


    fun placeToSelected(place: Place) {
        _filter.value!!.toRegionId = place.regionId
        _filter.value!!.toDistrictId = place.districtId
        applyFilter()
    }

    fun setDate(dayOfMonth: Int, month: Int, year: Int) {
        val properMonthIndex = month + 1
        val monthString =
            if (properMonthIndex.toString().length == 1) "0$properMonthIndex" else properMonthIndex.toString()
        _filter.value!!.departureDate = "$dayOfMonth.$monthString.$year"
        applyFilter()
    }

    fun dayTimePartsChecked(timeFirstPart: Boolean,
                            timeSecondPart: Boolean,
                            timeThirdPart: Boolean,
                            timeFourthPart: Boolean) {

        _filter.value!!.timeFirstPart = timeFirstPart
        _filter.value!!.timeSecondPart = timeSecondPart
        _filter.value!!.timeThirdPart = timeThirdPart
        _filter.value!!.timeFourthPart = timeFourthPart
    }

    fun seatCountChanged(count: Int?) {
        _filter.value!!.seat = count
    }


    private fun checkAppliedFiltersCount() {
        var appliedFilterCount = 0
        if (_filter.value!!.minPrice != null && _filter.value!!.maxPrice != null && (_filter.value!!.minPrice != MIN_PRICE || _filter.value!!.maxPrice != MAX_PRICE)) {
            appliedFilterCount++
        }
        if (_filter.value!!.airConditioner == true) appliedFilterCount++
        if (_filter.value!!.seat != null) appliedFilterCount++
        if (_filter.value!!.timeFirstPart == true || _filter.value!!.timeSecondPart == true || _filter.value!!.timeThirdPart == true || _filter.value!!.timeFourthPart == true) appliedFilterCount++
        _count.value = appliedFilterCount
    }


}