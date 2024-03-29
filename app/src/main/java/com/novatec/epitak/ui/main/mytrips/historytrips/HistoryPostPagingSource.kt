package com.novatec.epitak.ui.main.mytrips.historytrips

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.novatec.remote.AuthApi
import com.novatec.remote.model.DriverPostModel

private const val POST_OFFER_STARTING_PAGE_INDEX = 0

class HistoryPostPagingSource(private val authApi: AuthApi) :
    PagingSource<Int, DriverPostModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DriverPostModel> {
        val position = params.key ?: POST_OFFER_STARTING_PAGE_INDEX

        return try {
            val response =
                authApi.getHistoryPosts(position, params.loadSize)
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

    override fun getRefreshKey(state: PagingState<Int, DriverPostModel>): Int? {
            return state.anchorPosition
    }


}