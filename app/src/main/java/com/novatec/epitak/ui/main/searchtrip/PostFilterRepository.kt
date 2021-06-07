package com.novatec.epitak.ui.main.searchtrip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.novatec.domain.domainmodel.Filter
import com.novatec.remote.AuthApi
import javax.inject.Inject


class PostFilterRepository @Inject constructor(private val authApi: AuthApi) {

    fun getFilteredPosts(filter: LiveData<Filter>) =
        Pager(config = PagingConfig(
            pageSize = 25,
            maxSize = 100,
            enablePlaceholders = false
        ),
              pagingSourceFactory = {
                  Log.d("","")
                  PostFilterPagingSource(authApi, filter)
              }).liveData
}