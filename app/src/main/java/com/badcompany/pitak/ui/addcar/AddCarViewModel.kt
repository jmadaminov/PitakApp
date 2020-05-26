package com.badcompany.pitak.ui.addcar

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.CarColorAndModel
import com.badcompany.domain.domainmodel.CarColorBody
import com.badcompany.domain.domainmodel.CarModelBody
import com.badcompany.domain.domainmodel.PhotoBody
import com.badcompany.domain.usecases.GetCarColors
import com.badcompany.domain.usecases.GetCarModels
import com.badcompany.domain.usecases.UploadCarPhoto
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject

/**
 * Created by jahon on 28-Apr-20
 */
class AddCarViewModel @Inject constructor(private val uploadCarPhoto: UploadCarPhoto,
                                          private val getCarColors: GetCarColors,
                                          private val getCarModels: GetCarModels) :
    BaseViewModel() {

    private lateinit var getColorsAndModelsJob: Job
    val carAvatarResponse = SingleLiveEvent<ResultWrapper<PhotoBody>>()
    val carColorsResponse = SingleLiveEvent<ResultWrapper<List<CarColorBody>>>()
    val carModelsResponse = SingleLiveEvent<ResultWrapper<List<CarModelBody>>>()
    val carColorModelsResponse = SingleLiveEvent<ResultWrapper<CarColorAndModel>>()

    fun uploadCarPhoto(file: File) {
        carAvatarResponse.value = ResultWrapper.InProgress
        viewModelScope.launch {
            carAvatarResponse.value = uploadCarPhoto.execute(file)
        }
    }

    fun getCarColorsAndModels(token: String) {
//        val getColorsAndModelsJob = viewModelScope.launch {
//            val getColorsJob = launch {
//                carColorsResponse.value = getCarColors.execute(token)
//            }
//            val getModelsJob = launch {
//                carModelsResponse.value = getCarModels.execute(token)
//            }
//        }

        getColorsAndModelsJob  = launchGetColorsAndModelsJob(token)


        getColorsAndModelsJob.invokeOnCompletion {
            if (it == null) {
                if (carColorsResponse.value is ResultWrapper.Success && carModelsResponse.value is ResultWrapper.Success) {
                    Log.d("SUUUUUUUCCSSSSSEEEESSSS", " WORRKED")
                    carColorModelsResponse.value = ResultWrapper.Success(CarColorAndModel(
                        (carColorsResponse.value as ResultWrapper.Success).value,
                        (carModelsResponse.value as ResultWrapper.Success).value))
                } else {
                    carColorModelsResponse.value = ErrorWrapper.ResponseError(-1)
                    Log.d("NOT SUUcceess", " not WORRKED")
                }

            } else {
                carColorModelsResponse.value = ErrorWrapper.SystemError(it)
                Log.d("FAIL ", "cause " + it.localizedMessage)

            }


        }

    }


    fun launchGetColorsAndModelsJob(token: String) = viewModelScope.launch {
        carColorModelsResponse.value = ResultWrapper.InProgress
        val colors: Deferred<ResultWrapper<List<CarColorBody>>> = async {
            carColorsResponse.value = getCarColors.execute(token)
            carColorsResponse.value!!
        }
        val models: Deferred<ResultWrapper<List<CarModelBody>>> = async {
            carModelsResponse.value = getCarModels.execute(token)
            carModelsResponse.value!!
        }

        colors.await()
        models.await()
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}