package com.novatec.pitak.ui.addpost.preview

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.usecases.CreateDriverPost
import com.novatec.pitak.ui.BaseViewModel
import com.novatec.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi


class PreviewViewModel  @ViewModelInject constructor(private val createDriverPost: CreateDriverPost) :
    BaseViewModel() {

    val createResponse = SingleLiveEvent<ResultWrapper<DriverPost>>()


    @ExperimentalSplittiesApi
    fun createDriverPost(driverPost: DriverPost) {
        createResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = createDriverPost.execute(driverPost)

            withContext(Main) {
                createResponse.value = response
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }


}
