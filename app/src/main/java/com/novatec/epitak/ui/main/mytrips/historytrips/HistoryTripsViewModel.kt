package com.novatec.epitak.ui.main.mytrips.historytrips

import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.repository.DriverPostRepository
import com.novatec.domain.usecases.GetHistoryDriverPost
import com.novatec.epitak.ui.BaseViewModel
import com.novatec.epitak.util.SingleLiveEvent
import com.novatec.remote.model.DriverPostModel
import com.novatec.remote.model.PassengerPostModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

@HiltViewModel
class HistoryTripsViewModel  @Inject constructor(val historyPostRepository: HistoryPostRepository) :
    BaseViewModel() {


    var postOffers: LiveData<PagingData<DriverPostModel>> = MutableLiveData()
    fun getHistoryPost() {
        postOffers = historyPostRepository.getHistoryPosts().cachedIn(viewModelScope)
    }


}