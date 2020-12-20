package com.badcompany.pitak.ui.addpost.carandtext

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.CarDetails
import com.badcompany.domain.usecases.GetCars
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
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
