package com.badcompany.pitak.ui.main.mytrips.historytrips

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.interfaces.IOnPostActionListener
import com.badcompany.pitak.ui.main.MainViewModel
import com.badcompany.pitak.ui.viewgroups.HistoryPostItem
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_history_trips.*
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

class HistoryTripsFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    Fragment(R.layout.fragment_history_trips) {


    private val adapter = GroupAdapter<GroupieViewHolder>()
    val viewmodel: HistoryTripsViewModel by viewModels {
        viewModelFactory
    }


    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecyclerView()
        setupListeners()
        subscribeObservers()
        viewmodel.getHistoryPosts()
    }

    override fun onStart() {
        super.onStart()
        if (viewmodel.historyPostsResponse.value != ResultWrapper.InProgress) {
            swipeRefreshLayout.isRefreshing = true
        } else {
            swipeRefreshLayout.isRefreshing =
                viewmodel.historyPostsResponse.value == ResultWrapper.InProgress
        }
    }


    override fun onResume() {
        super.onResume()


    }

    private fun setupRecyclerView() {
        historyPostsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        historyPostsList.setHasFixedSize(true)
        historyPostsList.adapter = adapter

    }

    @ExperimentalSplittiesApi
    private fun setupListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            noHistoryPostsTxt.visibility = View.GONE
            viewmodel.getHistoryPosts()
        }
    }

    @ExperimentalSplittiesApi
    private fun subscribeObservers() {

//        activityViewModel.deletedOrderItem.observe(viewLifecycleOwner, Observer {
//            val order = it ?: return@Observer
//            adapter.remove(adapter.getItem(order.adapterPosition!!))
//            adapter.notifyItemRemoved(order.adapterPosition!!)
//            if (adapter.itemCount == 0) noActiveOrdersTxt.visibility = View.VISIBLE
//
//        })

//        activityViewModel.updatedOrderItem.observe(viewLifecycleOwner, Observer {
//            val order = it ?: return@Observer
//            val target = adapter.getItem(order.adapterPosition!!)
//            (target as ActiveOrderItem).order = order
//            adapter.notifyItemChanged(order.adapterPosition!!)
//        })

        viewmodel.historyPostsResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    swipeRefreshLayout.isRefreshing = false
                    Snackbar.make(swipeRefreshLayout,
                                  response.message!!,
                                  Snackbar.LENGTH_SHORT).show()

                }
                is ErrorWrapper.SystemError -> {
                    swipeRefreshLayout.isRefreshing = false
                    Snackbar.make(swipeRefreshLayout,
                                  response.err.localizedMessage.toString(),
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {
                    swipeRefreshLayout.isRefreshing = false
                    loadData(response.value)
                }
                ResultWrapper.InProgress -> {
                    swipeRefreshLayout.isRefreshing = true
                }
            }.exhaustive
        })

        viewmodel.cancelOrderReponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.ResponseError -> {
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


    var dialog: AlertDialog? = null

//    @ExperimentalSplittiesApi
//    val onOrderActionListener = object : IOnOrderActionListener {
//        override fun onModifyClick(order: OrderData) {
//            val dialog = EditOrderBottomSheetDialog(viewModelFactory)
//            val args = Bundle()
//            args.putParcelable(Constants.ARG_ORDER, order)
//            dialog.arguments = args
//            dialog.showsDialog
//            dialog.show(childFragmentManager, "Edit_Order_BSD")
//        }
//
//        override fun onEditClick(order: OrderData) {
//        }
//
//    }

    @ExperimentalSplittiesApi
    private fun loadData(orders: List<DriverPost>) {
        adapter.clear()
        if (orders.isEmpty()) noHistoryPostsTxt.visibility = View.VISIBLE
        else noHistoryPostsTxt.visibility = View.GONE

        orders.forEach {
            adapter.add(HistoryPostItem(it, onOrderActionListener))
        }
        adapter.notifyDataSetChanged()

    }


    private val onOrderActionListener = object : IOnPostActionListener {
        override fun onEditClick(post: DriverPost) {
        }

        override fun onCancelClick(post: DriverPost) {
        }

        override fun onDoneClick(post: DriverPost) {

        }
    }


}