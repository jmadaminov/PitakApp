package com.badcompany.pitak.ui.main.searchtrip

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.Filter
import com.badcompany.domain.domainmodel.PassengerPost
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.addcar.MyItemClickListener
import com.badcompany.pitak.ui.main.MainActivity
import com.badcompany.pitak.ui.viewgroups.LoadingItemSmall
import com.badcompany.pitak.ui.viewgroups.PassengerPostItem
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_search_trip.*
import javax.inject.Inject

class SearchTripFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    Fragment(R.layout.fragment_search_trip) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel: SearchTripViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).hideTabLayout()

//        change_password.setOnClickListener {
//            findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
//        }
//
//        logout_button.setOnClickListener {
//            viewModel.logout()
//        }
//
        setupRecyclerView()
        viewModel.getPassengerPost(Filter())
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.passengerPostsReponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    Snackbar.make(nestedScroll,
                                  response.message!!,
                                  Snackbar.LENGTH_SHORT).show()

                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(nestedScroll,
                                  response.err.localizedMessage.toString(),
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {
                    loadData(response.value)
                }
                ResultWrapper.InProgress -> {
                    addLoading()
                }
            }.exhaustive
        })

    }

    private fun setupRecyclerView() {
        passengerPosts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        passengerPosts.setHasFixedSize(true)
        passengerPosts.adapter = adapter
    }

    private fun addLoading() {
        adapter.clear()
        adapter.add(LoadingItemSmall())
        adapter.notifyDataSetChanged()
    }

    private fun loadData(value: List<PassengerPost>) {
        adapter.clear()

        value.forEach {
            adapter.add(PassengerPostItem(it, object : MyItemClickListener {
                override fun onClick(pos: Int) {
                    super.onClick(pos)
                }
            }))
        }
        adapter.notifyDataSetChanged()

    }






}