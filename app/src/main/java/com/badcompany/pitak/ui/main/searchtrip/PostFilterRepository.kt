package com.badcompany.pitak.ui.main.searchtrip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.badcompany.domain.domainmodel.Filter
import com.badcompany.remote.AuthorizedApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostFilterRepository @Inject constructor(private val authorizedApiService: AuthorizedApiService) {

    fun getFilteredPosts(filter: LiveData<Filter>) =
        Pager(config = PagingConfig(
            pageSize = 25,
            maxSize = 100,
            enablePlaceholders = false
        ),
              pagingSourceFactory = {
                  Log.d("","")
                  PostFilterPagingSource(authorizedApiService, filter)
              }).liveData
}