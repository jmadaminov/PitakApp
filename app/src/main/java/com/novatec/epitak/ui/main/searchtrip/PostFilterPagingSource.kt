package com.novatec.epitak.ui.main.searchtrip

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.novatec.domain.domainmodel.Filter
import com.novatec.remote.AuthApi
import com.novatec.remote.mapper.FilterMapper
import com.novatec.remote.model.PassengerPostModel

private const val POST_OFFER_STARTING_PAGE_INDEX = 0

class PostFilterPagingSource(
    private val authApi: AuthApi,
    private val filter: LiveData<Filter>
) : PagingSource<Int, PassengerPostModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PassengerPostModel> {
        val position = params.key ?: POST_OFFER_STARTING_PAGE_INDEX
        val fltr = FilterMapper().mapFromEntity(com.novatec.data.mapper.FilterMapper()
                                                    .mapToEntity(filter.value!!))

        return try {
            val response =
                authApi.filterPassengerPost(fltr, position, params.loadSize)
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

    override fun getRefreshKey(state: PagingState<Int, PassengerPostModel>): Int? {
        return state.anchorPosition
    }
}