package com.badcompany.pitak.ui.main.mytrips.activetrips

import androidx.lifecycle.viewModelScope
import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.usecases.DeleteDriverPost
import com.badcompany.domain.usecases.FinishDriverPost
import com.badcompany.domain.usecases.GetActiveDriverPost
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.AppPreferences
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

class ActiveTripsViewModel @Inject constructor(val getActiveDriverPost: GetActiveDriverPost,
                                               val deletePost: DeleteDriverPost,
                                               val finishPost: FinishDriverPost) : BaseViewModel() {

    val activePostsResponse = SingleLiveEvent<ResultWrapper<List<DriverPost>>>()
    val deletePostReponse = SingleLiveEvent<ResultWrapper<Int>>()
    val finishPostResponse = SingleLiveEvent<ResultWrapper<Int>>()

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

    @ExperimentalSplittiesApi
    fun deletePost(identifier: String, pos: Int) {
        deletePostReponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = deletePost.execute(hashMapOf(
                Pair(Constants.TXT_TOKEN, AppPreferences.token),
                Pair(Constants.TXT_ID, identifier),
                Pair(Constants.TXT_POSITION, pos)))

            withContext(Dispatchers.Main) {
                deletePostReponse.value = response
            }
        }
    }

    @ExperimentalSplittiesApi
    fun finishPost(identifier: String, pos: Int) {
        finishPostResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = finishPost.execute(hashMapOf(
                Pair(Constants.TXT_TOKEN, AppPreferences.token),
                Pair(Constants.TXT_ID, identifier),
                Pair(Constants.TXT_POSITION, pos)))

            withContext(Dispatchers.Main) {
                finishPostResponse.value = response
            }
        }
    }

}