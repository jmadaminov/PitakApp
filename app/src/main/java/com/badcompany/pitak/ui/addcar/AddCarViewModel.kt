package com.badcompany.pitak.ui.addcar

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResponseWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.*
import com.badcompany.domain.usecases.GetCarColors
import com.badcompany.domain.usecases.GetCarModels
import com.badcompany.domain.usecases.SaveCar
import com.badcompany.domain.usecases.UploadPhoto
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.AppPrefs
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.edit
import java.io.File

/**
 * Created by jahon on 28-Apr-20
 */
class AddCarViewModel @ViewModelInject constructor(private val uploadPhoto: UploadPhoto,
                                                   private val saveCar: SaveCar,
                                                   private val getCarColors: GetCarColors,
                                                   private val getCarModels: GetCarModels) :
    BaseViewModel() {

    val carSaveReponse = SingleLiveEvent<ResultWrapper<Car>>()
    val colorsAndModels = SingleLiveEvent<ResultWrapper<ColorsAndModels>>()
    val carAvatarResponse = SingleLiveEvent<ResultWrapper<PhotoBody>>()
    val carImgResponse = SingleLiveEvent<ResultWrapper<PhotoBody>>()

//    fun getCarSaveReponse(): SingleLiveEvent<ResultWrapper<String>> = carSaveReponse
//    fun getColorsAndModels(): SingleLiveEvent<ResultWrapper<ColorsAndModels>> = colorsAndModels
//    fun getCarAvatarResponse(): SingleLiveEvent<ResultWrapper<PhotoBody>> = carAvatarResponse
//    fun getCarImgResponse(): SingleLiveEvent<ResultWrapper<PhotoBody>> = carImgResponse


    @ExperimentalSplittiesApi
    @InternalCoroutinesApi
    fun getCarColorsAndModels() {
        viewModelScope.launch(IO) {
            withContext(Main) {
                colorsAndModels.value = ResultWrapper.InProgress
            }
            withContext(IO) {
                try {
                    val colors = async { getCarColors.execute() }
                    val models = async { getCarModels.execute() }
                    processResponses(colors.await(), models.await())
                } catch (e: Exception) {
                    withContext(Main) {
                        colorsAndModels.value = ErrorWrapper.SystemError(e)
                    }
                }
            }
        }
    }

    private fun processResponses(colorsResp: ResultWrapper<List<CarColorBody>>,
                                 modelsResp: ResultWrapper<List<CarModelBody>>) {
        viewModelScope.launch(Main) {
            if (colorsResp is ResultWrapper.Success && modelsResp is ResultWrapper.Success) {
                colorsAndModels.value =
                    ResultWrapper.Success(ColorsAndModels(colorsResp.value, modelsResp.value))
            } else {
                colorsAndModels.value =
                    if (colorsResp is ErrorWrapper) colorsResp else modelsResp as ErrorWrapper
            }
        }
    }


    fun uploadCarPhoto(file: File, isAvatar: Boolean = false) {
        val liveData = if (isAvatar) carAvatarResponse else carImgResponse
        liveData.value = ResultWrapper.InProgress
        viewModelScope.launch(IO) {
            val response = uploadPhoto.execute(file)
            withContext(Main) {
                liveData.value = response
            }
        }


    }


    fun saveCar(car: Car) {
        carSaveReponse.value = ResultWrapper.InProgress
        viewModelScope.launch(IO) {
            val response = saveCar.execute(car)
            withContext(Main) {

                carSaveReponse.value = response
            }

        }
    }


//    @ExperimentalCoroutinesApi
//    override fun onCleared() {
//        super.onCleared()
//        (appCtx as App).releaseAddCarComponent()
//    }
}