package com.novatec.pitak.ui.main.mytrips.historytrips

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.novatec.pitak.R
import com.novatec.pitak.ui.main.searchtrip.PostLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history_trips.*
import splitties.experimental.ExperimentalSplittiesApi


@AndroidEntryPoint
class HistoryTripsFragment : Fragment(R.layout.fragment_history_trips) {

    var postsAdapter = HistoryPostAdapter()

    val viewModel: HistoryTripsViewModel by viewModels()

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupViews()
        setupListeners()
        viewModel.getHistoryPost()
        subscribeObservers()
    }

    private fun setupViews() {

        rvPosts.adapter = postsAdapter.withLoadStateHeaderAndFooter(
            header = PostLoadStateAdapter { postsAdapter.retry() },
            footer = PostLoadStateAdapter { postsAdapter.retry() }
        )

        postsAdapter.addLoadStateListener { loadState ->
//            swipeRefreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading
            swipeRefreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading
            rvPosts.isVisible = loadState.source.refresh is LoadState.NotLoading
            tv_error.isVisible = loadState.source.refresh is LoadState.Error
            if (loadState.source.refresh is LoadState.Error) {
                tv_error.text = (loadState.source.refresh as LoadState.Error).error.localizedMessage
            }
            btn_retry.isVisible = loadState.source.refresh is LoadState.Error

            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && postsAdapter.itemCount < 1) {
                rvPosts.isVisible = false
                tv_error.isVisible = true
                tv_error.setText(R.string.no_history_posts)

            } else if (loadState.source.refresh !is LoadState.Error) {
                rvPosts.isVisible = true
                tv_error.isVisible = false
            }
        }
    }


    @ExperimentalSplittiesApi
    private fun setupListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            postsAdapter.refresh()
        }
    }

    @ExperimentalSplittiesApi
    private fun subscribeObservers() {
        viewModel.postOffers.observe(viewLifecycleOwner, {
            val value = it ?: return@observe
            postsAdapter.submitData(lifecycle, value)

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        postsAdapter.removeLoadStateListener { }
        rvPosts.adapter = null
    }
}