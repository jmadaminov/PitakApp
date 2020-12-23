package com.badcompany.pitak.ui.main.searchtrip

import androidx.paging.PagingSource
import com.badcompany.remote.AuthorizedApiService
import com.badcompany.remote.model.FilterModel
import com.badcompany.remote.model.PassengerPostModel
import com.bumptech.glide.load.HttpException
import java.io.IOException

private const val POST_OFFER_STARTING_PAGE_INDEX = 0

class PostFilterPagingSource(
    private val authorizedApiService: AuthorizedApiService,
    private val filter: FilterModel
) : PagingSource<Int, PassengerPostModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PassengerPostModel> {
        val position = params.key ?: POST_OFFER_STARTING_PAGE_INDEX

        return try {
            val response = authorizedApiService.filterPassengerPost(filter, position, params.loadSize)
            val posts = response.data?.data
            LoadResult.Page(
                data = posts!!,
                prevKey = if (position == POST_OFFER_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (posts.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }
}