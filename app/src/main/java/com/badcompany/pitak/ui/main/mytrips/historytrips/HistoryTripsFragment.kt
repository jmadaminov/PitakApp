package com.badcompany.pitak.ui.main.mytrips.historytrips

import android.os.Bundle
import android.util.Log.wtf
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.viewgroups.HistoryPostItem
import com.badcompany.pitak.ui.viewgroups.LoadingItemSmall
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history_trips.*
import splitties.experimental.ExperimentalSplittiesApi


@AndroidEntryPoint
class HistoryTripsFragment : Fragment(R.layout.fragment_history_trips) {


    private val adapter = GroupAdapter<GroupieViewHolder>()
    val viewModel: HistoryTripsViewModel by viewModels()

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecyclerView()
        setupListeners()
        subscribeObservers()
        viewModel.getHistoryPosts(0)
    }


    @ExperimentalSplittiesApi
    private fun setupRecyclerView() {
        historyPostsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        historyPostsList.setHasFixedSize(true)
        adapter.clear()
        historyPostsList.adapter = adapter

        historyPostsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (adapter.itemCount % 10 == 0) viewModel.getHistoryPosts(adapter.itemCount / 10)
                    wtf("-----", "end")
                }
            }
        })

    }

    @ExperimentalSplittiesApi
    private fun setupListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            noHistoryPostsTxt.visibility = View.GONE
            viewModel.getHistoryPosts(0)
        }
    }

    @ExperimentalSplittiesApi
    private fun subscribeObservers() {

        viewModel.historyPostsResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.RespError -> {
                    Snackbar.make(swipeRefreshLayout,
                                  response.message!!,
                                  Snackbar.LENGTH_SHORT).show()
                    removeLoadingMore()
                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(swipeRefreshLayout,
                                  response.err.localizedMessage.toString(),
                                  Snackbar.LENGTH_SHORT).show()
                    removeLoadingMore()
                }
                is ResultWrapper.Success -> {
                    removeLoadingMore()
                    loadData(response.value)
                }
                ResultWrapper.InProgress -> {
                    addLoading()
                }
            }.exhaustive
        })

        viewModel.cancelOrderReponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.RespError -> {
                    Snackbar.make(swipeRefreshLayout,
                                  response.message!!,
                                  Snackbar.LENGTH_SHORT).show()

                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(swipeRefreshLayout,
                                  response.err.localizedMessage.toString(),
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {

                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive
        })


    }

    private fun addLoading() {
        if (viewModel.currentPage == 0) {
            swipeRefreshLayout.isRefreshing = true
        } else {
            adapter.add(LoadingItemSmall())
            adapter.notifyItemInserted(adapter.itemCount - 1)
        }

    }

    private fun removeLoadingMore() {
        if (viewModel.currentPage == 0) {
            swipeRefreshLayout.isRefreshing = false
        } else {
            adapter.remove(adapter.getItem(adapter.itemCount - 1))
            adapter.notifyItemRemoved(adapter.itemCount - 1)
        }
    }


    @ExperimentalSplittiesApi
    private fun loadData(orders: List<DriverPost>) {
        if (viewModel.currentPage == 0) {
            adapter.clear()
            if (orders.isEmpty()) noHistoryPostsTxt.visibility = View.VISIBLE
            else noHistoryPostsTxt.visibility = View.GONE
        }

        orders.forEach { adapter.add(HistoryPostItem(it)) }
        adapter.notifyDataSetChanged()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        historyPostsList.adapter = null
    }
}