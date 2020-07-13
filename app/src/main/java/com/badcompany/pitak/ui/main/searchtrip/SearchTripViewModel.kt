package com.badcompany.pitak.ui.main.searchtrip

import androidx.lifecycle.viewModelScope
import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Filter
import com.badcompany.domain.domainmodel.PassengerPost
import com.badcompany.domain.usecases.GetPassengerPostWithFilter
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.AppPreferences
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

class SearchTripViewModel @Inject constructor(val getPassengerPostWithFilter: GetPassengerPostWithFilter) :
    BaseViewModel() {


    val passengerPostsReponse = SingleLiveEvent<ResultWrapper<List<PassengerPost>>>()
    var currentPage = 0

    @ExperimentalSplittiesApi
    fun getPassengerPost(filter: Filter) {
        passengerPostsReponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = getPassengerPostWithFilter.execute(hashMapOf(
                Pair(Constants.TXT_TOKEN, AppPreferences.token),
                Pair(Constants.TXT_LANG, AppPreferences.language),
                Pair(Constants.TXT_FILTER, filter)))

            withContext(Dispatchers.Main) {
                passengerPostsReponse.value = response
            }

        }

    }
}