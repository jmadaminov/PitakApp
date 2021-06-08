//package com.novatec.epitak.ui.driver_post
//
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.liveData
//import com.novatec.remote.AuthApi
//import javax.inject.Inject
//
//class PostOffersRepository @Inject constructor(private val authApi: AuthApi) {
//
//    fun getOffersForPost(id: Long) =
//        Pager(config = PagingConfig(
//            pageSize = 10,
//            maxSize = 100,
//            enablePlaceholders = false
//        ),
//              pagingSourceFactory = { PostOffersPagingSource(authApi, id) }).liveData
//}