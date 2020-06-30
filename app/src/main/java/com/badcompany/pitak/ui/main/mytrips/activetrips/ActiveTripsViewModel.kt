package com.badcompany.pitak.ui.main.mytrips.activetrips

import androidx.lifecycle.viewModelScope
import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.usecases.GetActiveDriverPost
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.AppPreferences
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

class ActiveTripsViewModel @Inject constructor(val getActiveDriverPost: GetActiveDriverPost) :
    BaseViewModel() {

    val activePostsResponse = SingleLiveEvent<ResultWrapper<List<DriverPost>>>()
    val cancelOrderReponse = SingleLiveEvent<ResultWrapper<String>>()
    val updateOrderReponse = SingleLiveEvent<ResultWrapper<String>>()

    @ExperimentalSplittiesApi
    fun getActivePosts() {
        activePostsResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = getActiveDriverPost.execute(hashMapOf(
                Pair(Constants.TXT_TOKEN, AppPreferences.token),
                Pair(Constants.TXT_LANG, AppPreferences.language)))

            withContext(Dispatchers.Main) {
                activePostsResponse.value = response
            }
        }
    }

}