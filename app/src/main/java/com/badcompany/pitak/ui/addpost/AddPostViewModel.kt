package com.badcompany.pitak.ui.addpost

import androidx.lifecycle.viewModelScope
import com.badcompany.core.Constants.TXT_CAR
import com.badcompany.core.Constants.TXT_TOKEN
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.*
import com.badcompany.domain.usecases.GetCarColors
import com.badcompany.domain.usecases.GetCarModels
import com.badcompany.domain.usecases.SaveCar
import com.badcompany.domain.usecases.UploadPhoto
import com.badcompany.pitak.App
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import splitties.init.appCtx
import java.io.File
import javax.inject.Inject

/**
 * Created by jahon on 28-Apr-20
 */
class AddPostViewModel @Inject constructor(
                                           /*private val saveCar: SaveCar,*/
                                           ) :
    BaseViewModel() {

//    val carImgResponse = SingleLiveEvent<ResultWrapper<PhotoBody>>()

//    @InternalCoroutinesApi
//    fun getCarColorsAndModels(token: String) {
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



    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        (appCtx as App).releaseAddCarComponent()
    }
}