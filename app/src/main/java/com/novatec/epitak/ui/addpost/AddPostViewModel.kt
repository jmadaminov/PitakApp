package com.novatec.epitak.ui.addpost

import androidx.lifecycle.viewModelScope
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.domainmodel.Place
import com.novatec.domain.usecases.CreateDriverPost
import com.novatec.domain.usecases.GetCars
import com.novatec.epitak.ui.BaseViewModel
import com.novatec.epitak.util.SingleLiveEvent
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
        departureDate = dateFormat.format(calendar.time)
    }

    var isPackage: Boolean = true
    var isEditing: Boolean = false
    var id: Long? = null
    var placeFrom: Place? = null
    var placeTo: Place? = null
    var departureDate: String = dateFormat.format(calendar.time)
    var timeFirstPart = true
    var timeSecondPart = true
    var timeThirdPart = true
    var timeFourthPart = true
    var car: CarDetails? = null
    var price: Int? = null
    var seat: Int = 1
//    var note: String? = null

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


}