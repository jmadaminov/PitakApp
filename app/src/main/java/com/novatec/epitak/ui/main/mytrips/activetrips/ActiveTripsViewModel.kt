package com.novatec.epitak.ui.main.mytrips.activetrips

import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.usecases.DeleteDriverPost
import com.novatec.domain.usecases.FinishDriverPost
import com.novatec.domain.usecases.GetActiveDriverPost
import com.novatec.epitak.ui.BaseViewModel
import com.novatec.epitak.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

@HiltViewModel
class ActiveTripsViewModel  @Inject constructor(val getActiveDriverPost: GetActiveDriverPost,
                                                         val deletePost: DeleteDriverPost,
                                                         val finishPost: FinishDriverPost) : BaseViewModel() {

    val activePostsResponse = SingleLiveEvent<ResultWrapper<List<DriverPost>>>()
    val deletePostReponse = SingleLiveEvent<ResultWrapper<String>>()
    val finishPostResponse = SingleLiveEvent<ResultWrapper<String>>()

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

    @ExperimentalSplittiesApi
    fun deletePost(identifier: String, pos: Int) {
        deletePostReponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = deletePost.execute(                identifier)

            withContext(Dispatchers.Main) {
                deletePostReponse.value = response
            }
        }
    }

    @ExperimentalSplittiesApi
    fun finishPost(identifier: String, pos: Int) {
        finishPostResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = finishPost.execute(                identifier)

            withContext(Dispatchers.Main) {
                finishPostResponse.value = response
            }
        }
    }



}