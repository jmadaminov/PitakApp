package com.badcompany.pitak.ui.addpost.preview

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.core.Constants
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.domain.usecases.CreateDriverPost
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.AppPreferences
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject


class PreviewViewModel  @ViewModelInject constructor(private val createDriverPost: CreateDriverPost) :
    BaseViewModel() {

    val createResponse = SingleLiveEvent<ResultWrapper<String>>()


    @ExperimentalSplittiesApi
    fun createDriverPost(driverPost: DriverPost) {
        createResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = createDriverPost.execute(
                hashMapOf(Pair(Constants.TXT_TOKEN, AppPreferences.token),
                          Pair(Constants.TXT_DRIVER_POST, driverPost)))

            withContext(Main) {
                createResponse.value = response
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }


}
