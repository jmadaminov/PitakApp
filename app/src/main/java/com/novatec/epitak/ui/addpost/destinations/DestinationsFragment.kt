package com.novatec.epitak.ui.addpost.destinations

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.core.exhaustive
import com.novatec.epitak.MapsActivity
import com.novatec.epitak.R
import com.novatec.epitak.ui.addpost.AddPostViewModel
import com.novatec.epitak.ui.viewgroups.PlaceFeedItemView
import com.novatec.epitak.util.AutoCompleteManager
import com.novatec.epitak.util.buildAutoCompleteManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_destinations.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.start


//@FlowPreview
//@ExperimentalCoroutinesApi
@ExperimentalSplittiesApi
@AndroidEntryPoint
class DestinationsFragment : Fragment(R.layout.fragment_destinations) {

    val args: DestinationsFragmentArgs by navArgs()

    private val viewModel: DestinationsViewModel by viewModels()
    private val activityViewModel: AddPostViewModel by activityViewModels()
    lateinit var navController: NavController


    lateinit var autoCompleteManager: AutoCompleteManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previewOrPlainCheck()
        setupAutoCompleteViews()
        setupListeners()
        updateNextButtonState()
        setupObservers()
        navController = findNavController()
    }

    private fun setupAutoCompleteViews() {
        autoCompleteManager = buildAutoCompleteManager {
            with { requireContext() }
            fromEditText { fromInput }
            toEditText { toInput }
            onQueryAction { queryStr, isFrom ->
                viewModel.getPlacesFeed(queryStr, isFrom)
            }
//            targetViewModel { viewModel }
            updateBtnStateAction {
                updateNextButtonState()
            }

            popUpClickAction { isFrom, item ->
                if (isFrom) viewModel.placeFrom = item.place
                else viewModel.placeTo = item.place
            }
        }
    }

    private fun previewOrPlainCheck() {
//        if (args.ISFROMPOSTPREVIEW) {
//            viewModel.placeTo = activityViewModel.placeTo
//            viewModel.placeFrom = activityViewModel.placeFrom
//            fromInput.setText(viewModel.placeFrom!!.regionName)
//            toInput.setText(viewModel.placeTo!!.regionName)
//        }
    }

    private fun setupListeners() {

//        navBack.setOnClickListener {
//            requireActivity().onBackPressed()
//        }

        navNext.setOnClickListener {
            activityViewModel.placeFrom = viewModel.placeFrom
            activityViewModel.placeTo = viewModel.placeTo

//            navController.navigate(if (args.ISFROMPOSTPREVIEW) R.id.action_destinationsFragment_to_previewFragment else R.id.action_destinationsFragment_to_dateTimeFragment)
        }

        fromInput.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus -> if (!hasFocus) autoCompleteManager.fromFeed?.dismissPopup() }
        toInput.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus -> if (!hasFocus) autoCompleteManager.fromFeed?.dismissPopup() }

        map.setOnClickListener {
            start<MapsActivity>()
        }

    }

    private fun setupObservers() {
        viewModel.placesResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.RespError -> {

                }
                is ErrorWrapper.SystemError -> {

                }
                is ResultWrapper.Success -> {
                    autoCompleteManager.fromPresenter.getAdr()?.let { adapter ->
                        adapter.clear()
                        response.value.forEach { place ->
                            adapter.add(PlaceFeedItemView(place, autoCompleteManager.fromPresenter))
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive

        })
//        viewModel.toPlacesResponse.observe(viewLifecycleOwner, Observer {
//            val response = it ?: return@Observer
//
//            when (response) {
//                is ErrorWrapper.RespError -> {
//
//                }
//                is ErrorWrapper.SystemError -> {
//
//                }
//                is ResultWrapper.Success -> {
//                    autoCompleteManager.toPresenter.getAdr()?.let { adapter ->
//                        adapter.clear()
//                        response.value.forEach { place ->
//                            adapter.add(PlaceFeedItemView(place, autoCompleteManager.toPresenter))
//                        }
//                        adapter.notifyDataSetChanged()
//                    }
//                }
//                ResultWrapper.InProgress -> {
//                }
//            }.exhaustive
//
//        })
    }


    override fun onPause() {
        super.onPause()
        autoCompleteManager.fromFeed?.dismissPopup()
        autoCompleteManager.toFeed?.dismissPopup()
    }

    override fun onResume() {
        super.onResume()
//        (activity as AddPostActivity).showActionBar()
    }

    private fun updateNextButtonState() {
        navNext.isEnabled = viewModel.placeFrom != null && viewModel.placeTo != null

        if (navNext.isEnabled) {
            val bg = navNext.background
            bg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                              PorterDuff.Mode.SRC_ATOP)
            navNext.background = bg
        } else {
            val bg = navNext.background
            bg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.ic_grey),
                              PorterDuff.Mode.SRC_ATOP)
            navNext.background = bg
        }
    }


}

