package com.badcompany.pitak.ui.addpost.choosedestinations

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Place
import com.badcompany.domain.usecases.GetPlacesFeed
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.AppPreferences
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject


class ChooseDestinationsViewModel @Inject constructor(private val getPlacesFeed: GetPlacesFeed) :
    BaseViewModel() {

    val fromPlacesResponse = SingleLiveEvent<ResultWrapper<List<Place>>>()

    @ExperimentalSplittiesApi
    fun getPlacesFeed(queryString: String, isFrom: Boolean = true) {
        fromPlacesResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                getPlacesFeed.execute(hashMapOf(Pair(Constants.TXT_TOKEN, AppPreferences.token),
                                                Pair(Constants.TXT_PLACE, queryString)))
            withContext(Main) {
                fromPlacesResponse.value = response
            }
        }

    }

    // A placeholder username validation check
    private fun isCodeValid(code: String): Boolean {
        return code.length == 5
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }


}
