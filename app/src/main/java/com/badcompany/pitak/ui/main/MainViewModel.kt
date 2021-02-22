package com.badcompany.pitak.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.badcompany.core.ResponseError
import com.badcompany.core.ResponseSuccess
import com.badcompany.core.exhaustive
import com.badcompany.domain.repository.UserRepository
import com.badcompany.pitak.App
import com.badcompany.pitak.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by jahon on 13-Apr-20
 */
class MainViewModel @ViewModelInject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    val isAppVersionDeprecated = MutableLiveData<Boolean>()

    fun getActiveAppVersions() {
        viewModelScope.launch(IO) {
            val response = userRepository.getActiveAppVersions()

            when (response) {
                is ResponseError -> {
                }
                is ResponseSuccess -> {
                   withContext(Main){
                    isAppVersionDeprecated.value = !response.value.contains(App.versionName)
                   }
                }
            }.exhaustive
        }


    }


}
