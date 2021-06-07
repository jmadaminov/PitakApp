package com.novatec.epitak.ui.main.mytrips.historytrips

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.novatec.remote.AuthApi
import javax.inject.Inject

class HistoryPostRepository @Inject constructor(private val authApi: AuthApi) {

    fun getHistoryPosts() =
        Pager(config = PagingConfig(
            pageSize = 25,
            maxSize = 100,
            enablePlaceholders = false
        ),
              pagingSourceFactory = {
                  Log.d("", "")
                  HistoryPostPagingSource(authApi)
              }).liveData
}