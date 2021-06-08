package com.novatec.epitak.ui.addcar

import androidx.lifecycle.viewModelScope
import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.*
import com.novatec.domain.usecases.GetCarColors
import com.novatec.domain.usecases.GetCarModels
import com.novatec.domain.usecases.SaveCar
import com.novatec.domain.usecases.UploadPhoto
import com.novatec.epitak.ui.BaseViewModel
import com.novatec.epitak.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by jahon on 28-Apr-20
 */
@HiltViewModel
class AddCarViewModel @Inject constructor(private val uploadPhoto: UploadPhoto,
                                          private val saveCar: SaveCar,
                                          private val getCarColors: GetCarColors,
                                          private val getCarModels: GetCarModels) :
    BaseViewModel() {

    val carSaveReponse = SingleLiveEvent<ResultWrapper<Car>>()
    val colorsAndModels = SingleLiveEvent<ResultWrapper<ColorsAndModels>>()
    val carAvatarResponse = SingleLiveEvent<ResultWrapper<PhotoBody>>()
    val carImgResponse = SingleLiveEvent<ResultWrapper<PhotoBody>>()

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


    fun uploadCarPhoto(bytes: ByteArray, isAvatar: Boolean = false) {
        val liveData = if (isAvatar) carAvatarResponse else carImgResponse
        liveData.value = ResultWrapper.InProgress
        viewModelScope.launch(IO) {
            val response = uploadPhoto.execute(bytes)
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

}