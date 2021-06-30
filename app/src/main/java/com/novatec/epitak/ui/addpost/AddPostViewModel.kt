package com.novatec.epitak.ui.addpost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.core.EPostType
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.usecases.CreateDriverPost
import com.novatec.domain.usecases.GetCars
import com.novatec.epitak.ui.BaseViewModel
import com.novatec.epitak.util.SingleLiveEvent
import com.novatec.epitak.viewobjects.DriverPostViewObj
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by jahon on 28-Apr-20
 */
@HiltViewModel
class AddPostViewModel @Inject constructor(private val createDriverPost: CreateDriverPost,
                                           private val getCars: GetCars) :
    BaseViewModel() {

    val dateFormat = SimpleDateFormat("dd.MM.yyyy")
    val calendar = Calendar.getInstance()

    fun setDate(dayOfMonth: Int, month: Int, year: Int) {
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        postToBeAdded.value?.departureDate = dateFormat.format(calendar.time)
    }

    var postToBeAdded = MutableLiveData(DriverPostViewObj(
        0,
        timeFirstPart = true,
        timeSecondPart = true,
        timeThirdPart = true,
        timeFourthPart = true,
        departureDate = dateFormat.format(calendar.time),
        seat = 1,
        pkg = true,
        postType = EPostType.DRIVER_SM
    ))


    val createResponse = SingleLiveEvent<ResultWrapper<DriverPost?>>()


    @ExperimentalSplittiesApi
    fun createDriverPost(driverPost: DriverPost) {
        createResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = createDriverPost.execute(driverPost)
            withContext(Dispatchers.Main) {
                createResponse.value = response
            }
        }
    }

    val carsResponse = SingleLiveEvent<ResultWrapper<List<CarDetails>>>()


    @ExperimentalSplittiesApi
    fun getCars() {
        carsResponse.value = ResultWrapper.InProgress

        viewModelScope.launch(Dispatchers.IO) {
            val response = getCars.execute()
            withContext(Dispatchers.Main) {
                carsResponse.value = response
            }
        }
    }

    fun canTakeParcel(checked: Boolean) {
        postToBeAdded.value.apply {
            if (!checked && this?.seat == 0) {
                seat = 1
                this.postType = EPostType.DRIVER_SM
            }
            this?.pkg = checked
        }
    }

    fun setSeatCound(count: Int) {
        postToBeAdded.value.apply {
            if (count == 0) {
                this?.postType = EPostType.DRIVER_PARCEL
                this?.pkg = true
            } else {
                this?.postType = EPostType.DRIVER_SM
            }
            this?.seat = count
        }
    }

    fun setEditingPost(driverPostViewObj: DriverPostViewObj) {
        postToBeAdded.value = driverPostViewObj
    }

    fun setPrice(price: Int) {
        postToBeAdded.value!!.price = price
    }

    fun setPostRemark(remark: String) {
        postToBeAdded.value?.remark = remark

    }


}