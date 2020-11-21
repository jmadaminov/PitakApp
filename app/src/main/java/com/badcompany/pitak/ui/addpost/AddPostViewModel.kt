package com.badcompany.pitak.ui.addpost

import androidx.hilt.lifecycle.ViewModelInject
import com.badcompany.domain.domainmodel.CarDetails
import com.badcompany.domain.domainmodel.Place
import com.badcompany.pitak.App
import com.badcompany.pitak.ui.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import splitties.init.appCtx
import javax.inject.Inject

/**
 * Created by jahon on 28-Apr-20
 */
class AddPostViewModel @ViewModelInject constructor() :
    BaseViewModel() {

    var isEditing: Boolean = false
    var id: Long? = null
    var placeFrom: Place? = null
    var placeTo: Place? = null
    var departureDate: String? = null
    var timeFirstPart = false
    var timeSecondPart = false
    var timeThirdPart = false
    var timeFourthPart = false
    var car: CarDetails? = null
    var price: Int? = null
    var seat: Int? = null
    var note: String? = null

//    val carImgResponse = SingleLiveEvent<ResultWrapper<PhotoBody>>()

//    @InternalCoroutinesApi
//    fun getCarColorsAndModels() {
//        viewModelScope.launch(IO) {
//            withContext(Main) {
//                colorsAndModels.value = ResultWrapper.InProgress
//            }
//            withContext(IO) {
//                try {
//                    val colors = async { getCarColors.execute(token) }
//                    val models = async { getCarModels.execute(token) }
//                    processResponses(colors.await(), models.await())
//                } catch (e: Exception) {
//                    withContext(Main) {
//                        colorsAndModels.value = ErrorWrapper.SystemError(e)
//                    }
//                }
//            }
//        }
//    }


//    @ExperimentalCoroutinesApi
//    override fun onCleared() {
//        super.onCleared()
//        (appCtx as App).releaseAddCarComponent()
//    }
}