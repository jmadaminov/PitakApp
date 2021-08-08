package com.novatec.epitak.ui.main.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.novatec.epitak.R
import com.novatec.epitak.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private val viewModel: NotificationsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        subscribeObservers()
        setupViews()
    }

    @ExperimentalSplittiesApi
    private fun setupViews() {
        (activity as MainActivity).hideTabLayout()

    }


    private fun subscribeObservers() {


    }


    private fun setupListeners() {


    }


}

