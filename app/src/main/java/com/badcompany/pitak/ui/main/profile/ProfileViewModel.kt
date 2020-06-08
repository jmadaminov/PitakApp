package com.badcompany.pitak.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.CarDetails
import com.badcompany.domain.usecases.GetCars
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProfileViewModel @Inject constructor(val getCarList: GetCars) : BaseViewModel() {


    val carsResponse = SingleLiveEvent<ResultWrapper<List<CarDetails>>>()

    fun getCarList(token:String) {
        carsResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO)  {
            carsResponse.value = getCarList.execute(token)

        }

    }

}