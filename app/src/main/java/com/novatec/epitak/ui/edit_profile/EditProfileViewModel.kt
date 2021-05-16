package com.novatec.epitak.ui.edit_profile

import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novatec.core.ResponseError
import com.novatec.core.ResponseSuccess
import com.novatec.core.ResultWrapper
import com.novatec.core.exhaustive
import com.novatec.domain.domainmodel.PhotoBody
import com.novatec.domain.repository.FileUploadRepository
import com.novatec.domain.repository.UserRepository
import com.novatec.epitak.util.AppPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.preferences.edit
import java.io.File


@HiltViewModel
class EditProfileViewModel @Inject constructor(private val userRepository: UserRepository,
                                                        private val fileUploadRepository: FileUploadRepository) :
    ViewModel() {


    var _isUpdating = MutableLiveData<Boolean>()
    val isUpdating: LiveData<Boolean> get() = _isUpdating


    var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    var _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess: LiveData<Boolean> get() = _updateSuccess


    fun updateProfile(name: String, surName: String) {
        _isUpdating.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.updateUserInfo(name,
                                                         surName,
                                                         (uploadPhotoResp.value as? ResultWrapper.Success)?.value?.id)
            withContext(Dispatchers.Main) {
                _isUpdating.value = false
                when (response) {
                    is ResponseError -> {
                        _errorMessage.value = response.message
                    }
                    is ResponseSuccess -> {
                        AppPrefs.edit {
                            this.name = name
                            this.surname = surName
                            (uploadPhotoResp.value as? ResultWrapper.Success)?.value?.link?.let {
                                this.avatar = it
                            }
                        }

                        _updateSuccess.value = true
                    }
                }.exhaustive
            }
        }
    }

    var _uploadPhotoResp = MutableLiveData<ResultWrapper<PhotoBody>>()
    val uploadPhotoResp: LiveData<ResultWrapper<PhotoBody>> get() = _uploadPhotoResp

    fun uploadAvatar(file: File) {
        _uploadPhotoResp.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = fileUploadRepository.uploadPhoto(file)
            withContext(Dispatchers.Main) {
                _uploadPhotoResp.value = response
            }
        }
    }

}