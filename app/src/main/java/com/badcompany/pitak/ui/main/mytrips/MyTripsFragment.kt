package com.badcompany.pitak.ui.main.mytrips

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.R
import javax.inject.Inject

class MyTripsFragment  @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    Fragment(R.layout.fragment_my_trips) {

    private val viewModel: MyTripsViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

//        change_password.setOnClickListener {
//            findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
//        }
//
//        logout_button.setOnClickListener {
//            viewModel.logout()
//        }
//
//        subscribeObservers()
    }
}