//package com.novatec.epitak.ui.addpost.preview
//
//import javax.inject.Inject
//import androidx.lifecycle.viewModelScope
//import com.novatec.core.ResultWrapper
//import com.novatec.domain.domainmodel.DriverPost
//import com.novatec.domain.usecases.CreateDriverPost
//import com.novatec.epitak.ui.BaseViewModel
//import com.novatec.epitak.util.SingleLiveEvent
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Dispatchers.Main
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import splitties.experimental.ExperimentalSplittiesApi
//
//
//@HiltViewModel
//class PreviewViewModel  @Inject constructor(private val createDriverPost: CreateDriverPost) :
//    BaseViewModel() {
//
//    val createResponse = SingleLiveEvent<ResultWrapper<DriverPost?>>()
//
//
//    @ExperimentalSplittiesApi
//    fun createDriverPost(driverPost: DriverPost) {
//        createResponse.value = ResultWrapper.InProgress
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = createDriverPost.execute(driverPost)
//
//            withContext(Main) {
//                createResponse.value = response
//            }
//        }
//    }
//
//}
