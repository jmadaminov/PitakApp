package com.novatec.epitak.ui.main

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.core.ResponseError
import com.novatec.core.ResponseSuccess
import com.novatec.core.exhaustive
import com.novatec.domain.repository.UserRepository
import com.novatec.epitak.App
import com.novatec.epitak.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by jahon on 13-Apr-20
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val userRepository: UserRepository) :
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
