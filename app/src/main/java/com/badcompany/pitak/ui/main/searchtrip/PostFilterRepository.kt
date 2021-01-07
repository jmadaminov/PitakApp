package com.badcompany.pitak.ui.main.searchtrip

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.badcompany.domain.domainmodel.Filter
import com.badcompany.remote.ApiService
import com.badcompany.remote.AuthorizedApiService
import com.badcompany.remote.model.FilterModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostFilterRepository @Inject constructor(private val authorizedApiService: ApiService) {

    fun getFilteredPosts(filter: Filter) =
        Pager(config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
              pagingSourceFactory = { PostFilterPagingSource(authorizedApiService, filter) }).liveData
}