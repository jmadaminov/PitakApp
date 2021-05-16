package com.novatec.epitak.ui.addpost.carandtext

import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.domain.usecases.GetCars
import com.novatec.epitak.ui.BaseViewModel
import com.novatec.epitak.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi


@HiltViewModel
class CarAndTextViewModel @Inject constructor(private val getCars: GetCars) :
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
