package com.novatec.pitak.ui.addpost.carandtext

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.domain.usecases.GetCars
import com.novatec.pitak.ui.BaseViewModel
import com.novatec.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi


class CarAndTextViewModel @ViewModelInject constructor(private val getCars: GetCars) :
    BaseViewModel() {

    val carsResponse = SingleLiveEvent<ResultWrapper<List<CarDetails>>>()


    @ExperimentalSplittiesApi
    fun getCars() {
        carsResponse.value = ResultWrapper.InProgress

        viewModelScope.launch(Dispatchers.IO) {
            val response = getCars.execute()
            withContext(Main) {
               carsResponse.value = response
            }
        }
    }


}
