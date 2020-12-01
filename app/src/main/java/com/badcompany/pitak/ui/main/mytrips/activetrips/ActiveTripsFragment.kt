package com.badcompany.pitak.ui.main.mytrips.activetrips

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.driver_post.DriverPostActivity
import com.badcompany.pitak.ui.driver_post.EXTRA_POST_ID
import com.badcompany.pitak.ui.viewgroups.ActivePostItem
import com.badcompany.pitak.viewobjects.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_driver_post.*
import kotlinx.android.synthetic.main.fragment_active_trips.*
import kotlinx.android.synthetic.main.fragment_active_trips.swipeRefreshLayout
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.start

@AndroidEntryPoint
class ActiveTripsFragment : Fragment(R.layout.fragment_active_trips) {


    private val adapter = GroupAdapter<GroupieViewHolder>()
    val viewModel: ActiveTripsViewModel by viewModels()

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupViews()
        attachListeners()
        subscribeObservers()
        viewModel.getActivePosts()
    }

    private fun setupViews() {
        activeOrdersList.adapter = adapter
    }

    @ExperimentalSplittiesApi
    private fun attachListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            noActiveOrdersTxt.visibility = View.GONE
            viewModel.getActivePosts()
        }
    }

    @ExperimentalSplittiesApi
    private fun subscribeObservers() {

        viewModel.activePostsResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.RespError -> {
                    swipeRefreshLayout.isRefreshing = false
                    noActiveOrdersTxt.visibility = View.VISIBLE
                    noActiveOrdersTxt.text = response.message
                    activeOrdersList.visibility = View.INVISIBLE
                }
                is ErrorWrapper.SystemError -> {
                    noActiveOrdersTxt.visibility = View.VISIBLE
                    noActiveOrdersTxt.text = response.err.localizedMessage
                    activeOrdersList.visibility = View.INVISIBLE
                    swipeRefreshLayout.isRefreshing = false

                }
                is ResultWrapper.Success -> {
                    noActiveOrdersTxt.visibility = View.INVISIBLE
                    activeOrdersList.visibility = View.VISIBLE
                    swipeRefreshLayout.isRefreshing = false
                    loadData(response.value)
                }
                ResultWrapper.InProgress -> {
                    noActiveOrdersTxt.visibility = View.INVISIBLE
                    activeOrdersList.visibility = View.INVISIBLE
                    swipeRefreshLayout.isRefreshing = true
                }
            }.exhaustive
        })
    }

    @ExperimentalSplittiesApi
    private fun loadData(orders: List<DriverPost>) {
        adapter.clear()
        if (orders.isEmpty()) {
            noActiveOrdersTxt.visibility = View.VISIBLE
            noActiveOrdersTxt.text = getString(R.string.no_active_orders)
        } else noActiveOrdersTxt.visibility = View.GONE

        orders.forEach { post ->
            adapter.add(ActivePostItem(post) {
                start<DriverPostActivity> { putExtra(EXTRA_POST_ID, post.id) }
            })
        }
        adapter.notifyDataSetChanged()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        activeOrdersList.adapter = null
    }
}