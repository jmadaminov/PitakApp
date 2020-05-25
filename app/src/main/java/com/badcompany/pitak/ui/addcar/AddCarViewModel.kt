package com.badcompany.pitak.ui.addcar

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.CarColorBody
import com.badcompany.domain.domainmodel.CarModelBody
import com.badcompany.domain.domainmodel.PhotoBody
import com.badcompany.domain.usecases.GetCarColors
import com.badcompany.domain.usecases.GetCarModels
import com.badcompany.domain.usecases.UploadCarPhoto
import com.badcompany.pitak.ui.BaseViewModel
import com.badcompany.pitak.util.SingleLiveEvent
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * Created by jahon on 28-Apr-20
 */
class AddCarViewModel @Inject constructor(private val uploadCarPhoto: UploadCarPhoto,
                                          private val getCarColors: GetCarColors,
                                          private val getCarModels: GetCarModels) :
    BaseViewModel() {

    val carAvatarResponse = SingleLiveEvent<ResultWrapper<PhotoBody>>()
    val carColorsResponse = SingleLiveEvent<ResultWrapper<List<CarColorBody>>>()
    val carModelsResponse = SingleLiveEvent<ResultWrapper<List<CarModelBody>>>()

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

        val getColorsAndModelsJob = viewModelScope.launch {
            val colors: Deferred<ResultWrapper<List<CarColorBody>>> = async {
                getCarColors.execute(token)
            }
            val models: Deferred<ResultWrapper<List<CarModelBody>>> = async {
                getCarModels.execute(token)
            }

            colors.await()
            models.await()
        }.invokeOnCompletion {
            if (it == null) {

            } else {
                Log.d("COLOR MODELFAIL", it.message)
            }
        }


    }


}