package com.novatec.pitak.ui.addpost.preview

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.novatec.core.*
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.pitak.R
import com.novatec.pitak.ui.addcar.MyItemClickListener
import com.novatec.pitak.ui.addpost.AddPostViewModel
import com.novatec.pitak.ui.viewgroups.CarItemSelectionView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_preview.*
import splitties.experimental.ExperimentalSplittiesApi

@AndroidEntryPoint
class PreviewFragment : Fragment(R.layout.fragment_preview) {


    private val adapter = GroupAdapter<GroupieViewHolder>()

    private val activityViewModel: AddPostViewModel by activityViewModels()

    private val viewModel: PreviewViewModel by viewModels()
    lateinit var navController: NavController


    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
        setupViews()
        navController = findNavController()


        setupCarList()
    }

    private fun setupViews() {
        if (activityViewModel.isEditing) {
//            navBack.visibility = View.INVISIBLE
            postCreate.text = getString(R.string.update)
        }

        if (activityViewModel.placeFrom?.name == null && activityViewModel.placeFrom?.districtName == null) {
            labelFromRegion.isVisible = false
            labelFrom.text = activityViewModel.placeFrom?.regionName
        } else {
            labelFromRegion.isVisible = true
            labelFromRegion.text =
                activityViewModel.placeFrom?.regionName ?: activityViewModel.placeFrom?.name
            labelFrom.text = activityViewModel.placeFrom?.districtName
        }

        if (activityViewModel.placeTo?.name == null && activityViewModel.placeTo?.districtName == null) {
            labelToRegion.isVisible = false
            labelTo.text = activityViewModel.placeTo?.regionName
        } else {
            labelToRegion.isVisible = true
            labelToRegion.text =
                activityViewModel.placeTo?.regionName ?: activityViewModel.placeTo?.name
            labelTo.text = activityViewModel.placeTo?.districtName
        }


        var time = ""
        if (activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = getString(R.string.anytime)
        } else if (activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && !activityViewModel.timeFourthPart) {
            time = "00:00 - 18:00"
        } else if (activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && !activityViewModel.timeThirdPart && !activityViewModel.timeFourthPart) {
            time = "00:00 - 12:00"
        } else if (activityViewModel.timeFirstPart && !activityViewModel.timeSecondPart && !activityViewModel.timeThirdPart && !activityViewModel.timeFourthPart) {
            time = "00:00 - 6:00"
        } else if (!activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "6:00 - 00:00"
        } else if (!activityViewModel.timeFirstPart && !activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "12:00 - 00:00"
        } else if (!activityViewModel.timeFirstPart && !activityViewModel.timeSecondPart && !activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "18:00 - 00:00"
        } else if (activityViewModel.timeFirstPart && !activityViewModel.timeSecondPart && !activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "00:00 - 6:00, 18:00-00:00"
        } else if (!activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && !activityViewModel.timeFourthPart) {
            time = "6:00 - 18:00"
        } else if (activityViewModel.timeFirstPart && !activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "00:00 - 6:00, 12:00 - 00:00"
        } else if (activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && !activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "00:00 - 12:00, 18:00 - 00:00"
        } else if (activityViewModel.timeFirstPart && !activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && !activityViewModel.timeFourthPart) {
            time = "00:00 - 6:00, 12:00 - 18:00"
        } else if (!activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && !activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "6:00 - 12:00, 18:00 - 00:00"
        }

        dateTime.text =
            getString(R.string.departure_date_and_time, activityViewModel.departureDate, time)

        priceAndSeat.text = getString(R.string.price_and_seats_format,
                                      activityViewModel.price.toString(),
                                      activityViewModel.seat.toString())


        if (activityViewModel.note.isNullOrBlank()) {
            note.visibility = View.GONE
        } else {
            note.visibility = View.VISIBLE
            note.text = activityViewModel.note
        }


    }


    @ExperimentalSplittiesApi
    private fun setupListeners() {


//        navBack.setOnClickListener {
//            requireActivity().onBackPressed()
//        }

        layoutDestinations.setOnClickListener {
            navController.navigate(PreviewFragmentDirections.actionPreviewFragmentToDestinationsFragment(
                true))
        }
        dateTime.setOnClickListener {
            navController.navigate(PreviewFragmentDirections.actionPreviewFragmentToDateTimeFragment(
                true))
        }
        priceAndSeat.setOnClickListener {
            navController.navigate(PreviewFragmentDirections.actionPreviewFragmentToPriceAndSeatFragment(
                true))
        }
        note.setOnClickListener {
            navController.navigate(PreviewFragmentDirections.actionPreviewFragmentToCarAndTextFragment(
                true))
        }

        postCreate.setOnClickListener {

            viewModel.createDriverPost(DriverPost(activityViewModel.id,
                                                  activityViewModel.placeFrom!!,
                                                  activityViewModel.placeTo!!,
                                                  activityViewModel.price!!,
                                                  activityViewModel.departureDate!!,
                                                  null,
                                                  activityViewModel.timeFirstPart,
                                                  activityViewModel.timeSecondPart,
                                                  activityViewModel.timeThirdPart,
                                                  activityViewModel.timeFourthPart,
                                                  activityViewModel.car!!.id!!,
                                                  null,
                                                  activityViewModel.note,
                                                  activityViewModel.seat!!,
                                                  0,
                                                  null,
                                                  activityViewModel.seat!!,
                                                  EPostStatus.CREATED,
                                                  activityViewModel.isPackage,
                                                  null,
                                                  Constants.DRIVER_POST_SIMPLE))
        }

    }

    private fun setupCarList() {
        selectedCarList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        selectedCarList.setHasFixedSize(true)
        adapter.clear()
        selectedCarList.adapter = adapter
        adapter.add(CarItemSelectionView(activityViewModel.car!!, object : MyItemClickListener {
            override fun onClick(pos: Int, view: View) {
                super.onClick(pos)
                navController.navigate(PreviewFragmentDirections.actionPreviewFragmentToCarAndTextFragment(
                    true))
            }
        }))
    }

    @ExperimentalSplittiesApi
    private fun setupObservers() {
        viewModel.createResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.RespError -> {
                    postCreate.revertAnimation()
                    Snackbar.make(scrollView, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ErrorWrapper.SystemError -> {
                    postCreate.revertAnimation()
                    Snackbar.make(scrollView,
                                  response.err.localizedMessage!!,
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {
                    postCreate.stopAnimation()
                    requireActivity().setResult(RESULT_OK)
                    requireActivity().finish()
                }
                ResultWrapper.InProgress -> {
                    postCreate.startAnimation()
                }
            }.exhaustive

        })

    }


}




