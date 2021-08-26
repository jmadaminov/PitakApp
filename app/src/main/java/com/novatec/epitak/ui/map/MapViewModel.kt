package com.novatec.epitak.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.core.ResultWrapper
import com.novatec.domain.repository.MapRepository
import com.novatec.epitak.App
import com.novatec.epitak.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(val api: MapRepository) : BaseViewModel() {

    private val mapFileResp = MutableLiveData<ResultWrapper<String>>()

    fun getMapZip() {
        viewModelScope.launch(IO) {
            val responseBody = api.downloadmaps(App.getInstance().applicationInfo.dataDir)
            mapFileResp.postValue(responseBody)
        }
    }


}