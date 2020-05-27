package com.badcompany.pitak.ui.addcar

import androidx.lifecycle.viewModelScope
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.CarColorBody
import com.badcompany.domain.domainmodel.CarModelBody
import com.badcompany.domain.domainmodel.ColorsAndModels
import com.badcompany.domain.domainmodel.PhotoBody
import com.badcompany.domain.usecases.GetCarColors
import com.badcompany.domain.usecases.GetCarModels
import com.badcompany.domain.usecases.UploadCarPhoto
import com.badcompany.pitak.App
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import splitties.init.appCtx
import java.io.File
import javax.inject.Inject

/**
 * Created by jahon on 28-Apr-20
 */
class AddCarViewModel @Inject constructor(private val uploadCarPhoto: UploadCarPhoto,
                                          private val getCarColors: GetCarColors,
                                          private val getCarModels: GetCarModels) :
    BaseViewModel() {

    val colorsAndModels = SingleLiveEvent<ResultWrapper<ColorsAndModels>>()

    @InternalCoroutinesApi
    fun getCarColorsAndModels(token: String) {
        viewModelScope.launch {
            colorsAndModels.value = ResultWrapper.InProgress
            try {
                val colors = async { getCarColors.execute(token) }
                val models = async { getCarModels.execute(token) }
                processResponses(colors.await(), models.await())
            } catch (e: Exception) {
                colorsAndModels.value = ErrorWrapper.SystemError(e)
            }
        }
    }

    private fun processResponses(colorsResp: ResultWrapper<List<CarColorBody>>,
                                 modelsResp: ResultWrapper<List<CarModelBody>>) {
        if (colorsResp is ResultWrapper.Success && modelsResp is ResultWrapper.Success) {
            colorsAndModels.value =
                ResultWrapper.Success(ColorsAndModels(colorsResp.value, modelsResp.value))
        } else {
            colorsAndModels.value =
                if (colorsResp is ErrorWrapper) colorsResp else modelsResp as ErrorWrapper
        }
    }


    //        getColorsAndModelsJob = viewModelScope.launch {
//            carColorModelsResponse.value = ResultWrapper.InProgress
//            val colors: Deferred<ResultWrapper<List<CarColorBody>>> = async {
//                carColorsResponse.value = getCarColors.execute(token)
//                carColorsResponse.value!!
//            }
//            yield()
//            val models: Deferred<ResultWrapper<List<CarModelBody>>> = async {
//                carModelsResponse.value = getCarModels.execute(token)
//                carModelsResponse.value!!
//            }
//
//            colors.await()
//            models.await()
//        }
//        getColorsAndModelsJob.invokeOnCompletion {
//            if (it == null) {
//                if (carColorsResponse.value is ResultWrapper.Success && carModelsResponse.value is ResultWrapper.Success) {
//                    Log.d("SUUUUUUUCCSSSSSEEEESSSS", " WORRKED")
//                    carColorModelsResponse.value = ResultWrapper.Success(CarColorAndModel(
//                        (carColorsResponse.value as ResultWrapper.Success).value,
//                        (carModelsResponse.value as ResultWrapper.Success).value))
//                } else {
//                    carColorModelsResponse.value = ErrorWrapper.ResponseError(-1)
//                    Log.d("NOT SUUcceess", " not WORRKED")
//                }
//            } else {
//                carColorModelsResponse.value = ErrorWrapper.SystemError(it)
//                Log.d("FAIL ", "cause " + it.localizedMessage)
//            }
//        }

    val carAvatarResponse = SingleLiveEvent<ResultWrapper<PhotoBody>>()
    fun uploadCarPhoto(file: File) {
        carAvatarResponse.value = ResultWrapper.InProgress
        viewModelScope.launch { carAvatarResponse.value = uploadCarPhoto.execute(file) }
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        (appCtx as App).releaseAddCarComponent()
    }
}