package com.badcompany.pitak.ui.main.mytrips.historytrips

import androidx.lifecycle.viewModelScope
import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.usecases.GetHistoryDriverPost
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.AppPreferences
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

class HistoryTripsViewModel @Inject constructor(val getHistoryDriverPost: GetHistoryDriverPost) :
    BaseViewModel() {

    val historyPostsResponse = SingleLiveEvent<ResultWrapper<List<DriverPost>>>()
    val cancelOrderReponse = SingleLiveEvent<ResultWrapper<String>>()
    val updateOrderReponse = SingleLiveEvent<ResultWrapper<String>>()
    var currentPage = 0

    @ExperimentalSplittiesApi
    fun getHistoryPosts(page: Int) {
        currentPage = page
        historyPostsResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = getHistoryDriverPost.execute(hashMapOf(
                Pair(Constants.TXT_TOKEN, AppPreferences.token),
                Pair(Constants.TXT_LANG, AppPreferences.language),
                Pair(Constants.TXT_PAGE, page)))

            withContext(Dispatchers.Main) {
                historyPostsResponse.value = response
            }

        }

    }

}