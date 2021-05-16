package com.novatec.epitak.ui.addpost.carandtext

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
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.epitak.R
import com.novatec.epitak.ui.addcar.MyItemClickListener
import com.novatec.epitak.ui.addpost.AddPostViewModel
import com.novatec.epitak.ui.viewgroups.CarItemSelectionView
import com.novatec.epitak.ui.viewgroups.ErrorTextItem
import com.novatec.epitak.ui.viewgroups.LoadingItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_car_and_note.*
import splitties.experimental.ExperimentalSplittiesApi


@AndroidEntryPoint
class CarAndTextFragment : Fragment(R.layout.fragment_car_and_note) {

    val args: CarAndTextFragmentArgs by navArgs()


    private var selectedCar: CarDetails? = null
    private val adapter = GroupAdapter<GroupieViewHolder>()

    private val activityViewModel: AddPostViewModel by activityViewModels()

    private val viewModel: CarAndTextViewModel by viewModels()

    //    val args: PhoneConfirmFragmentArgs by navArgs()
    lateinit var navController: NavController

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.ISFROMPOSTPREVIEW) {
            noteInput.setText(activityViewModel.note)
        }

        setupListeners()
        setupCarList()
        setupObservers()

        navController = findNavController()

        viewModel.getCars()
        updateNextButtonState()
    }

    private fun setupCarList() {
        carsList.adapter = adapter

    }


    @ExperimentalSplittiesApi
    private fun setupListeners() {

        navNext.setOnClickListener {
            activityViewModel.car = selectedCar
            activityViewModel.note =
                if (!noteInput.text.isNullOrBlank()) noteInput.text.toString() else ""


            navController.navigate(if (args.ISFROMPOSTPREVIEW) R.id.action_carAndTextFragment_to_previewFragment else R.id.action_carAndTextFragment_to_previewFragment)
        }


    }


    @ExperimentalSplittiesApi
    private fun setupObservers() {
        viewModel.carsResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.RespError -> {
                    showErrorMessage(response.message)
                }
                is ErrorWrapper.SystemError -> {
                    showErrorMessage(response.err.localizedMessage)
                }
                is ResultWrapper.Success -> {
                    populateCarsList(response.value)
                }
                ResultWrapper.InProgress -> {
                    adapter.clear()
                    adapter.add(LoadingItem())
                }
            }.exhaustive

        })

    }

    private fun showErrorMessage(message: String?) {

        adapter.clear()
        adapter.add(ErrorTextItem(message))

    }

    private fun populateCarsList(cars: List<CarDetails>) {
        adapter.clear()
        cars.forEach { car ->
            if (car.def!!) {
                selectedCar = car
            }
            adapter.add(CarItemSelectionView(car, object : MyItemClickListener {
                override fun onClick(pos: Int, view: View) {
                    super.onClick(pos, view)
                    for (i in 0 until adapter.itemCount) {
                        (adapter.getItem(i) as CarItemSelectionView).car.def = false
                    }
                    car.def = true
                    adapter.notifyDataSetChanged()
                }
            }))
        }
        updateNextButtonState()
    }

    private fun updateNextButtonState() {
        navNext.isEnabled = selectedCar != null

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

    override fun onPause() {
        super.onPause()

    }

    @ExperimentalSplittiesApi
    override fun onResume() {
        super.onResume()
        viewModel.getCars()
//        (activity as AddPostActivity).showActionBar()
    }


}




