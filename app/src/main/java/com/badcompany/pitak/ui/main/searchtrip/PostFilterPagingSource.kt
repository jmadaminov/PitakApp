package com.badcompany.pitak.ui.main.searchtrip

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.badcompany.domain.domainmodel.Filter
import com.badcompany.pitak.util.valueNN
import com.badcompany.remote.ApiService
import com.badcompany.remote.AuthorizedApiService
import com.badcompany.remote.mapper.FilterMapper
import com.badcompany.remote.model.PassengerPostModel

private const val POST_OFFER_STARTING_PAGE_INDEX = 0

class PostFilterPagingSource(
    private val authorizedApiService: AuthorizedApiService,
    private val filter: LiveData<Filter>
) : PagingSource<Int, PassengerPostModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PassengerPostModel> {
        val position = params.key ?: POST_OFFER_STARTING_PAGE_INDEX
        val fltr = FilterMapper().mapFromEntity(com.badcompany.data.mapper.FilterMapper()
                                                    .mapToEntity(filter.valueNN))

        return try {
            val response =
                authorizedApiService.filterPassengerPost(fltr, position, params.loadSize)
            val posts = response.data?.data
            LoadResult.Page(
                data = posts!!,
                prevKey = if (position == POST_OFFER_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (posts.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

    }
}