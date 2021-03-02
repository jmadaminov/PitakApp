package com.novatec.pitak.ui.main.mytrips.historytrips

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.repository.DriverPostRepository
import com.novatec.domain.usecases.GetHistoryDriverPost
import com.novatec.pitak.ui.BaseViewModel
import com.novatec.pitak.util.SingleLiveEvent
import com.novatec.remote.model.DriverPostModel
import com.novatec.remote.model.PassengerPostModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

class HistoryTripsViewModel  @ViewModelInject constructor(val historyPostRepository: HistoryPostRepository) :
    BaseViewModel() {


    var postOffers: LiveData<PagingData<DriverPostModel>> = MutableLiveData()
    fun getHistoryPost() {
        postOffers = historyPostRepository.getHistoryPosts().cachedIn(viewModelScope)
    }


}