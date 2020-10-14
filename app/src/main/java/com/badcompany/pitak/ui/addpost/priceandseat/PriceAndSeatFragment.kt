package com.badcompany.pitak.ui.addpost.priceandseat

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.addpost.AddPostViewModel
import com.badcompany.pitak.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_price_and_seat.*
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject


//@FlowPreview
//@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PriceAndSeatFragment @Inject constructor(/*private val viewModelFactory: ViewModelProvider.Factory*/) :
    Fragment(R.layout.fragment_price_and_seat) {

    val args: PriceAndSeatFragmentArgs by navArgs()


    private var passengerCount: Int? = null


    private val activityViewModel: AddPostViewModel by activityViewModels() /*{
        viewModelFactory
    }*/

    //    val args: PhoneConfirmFragmentArgs by navArgs()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        activityViewModel.cancelActiveJobs()
    }

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupNumberPicker()

        if (args.ISFROMPOSTPREVIEW) {
            priceInput.setText(activityViewModel.price.toString())
            seatsNumberPicker.value = activityViewModel.seat!!
        }


        setupObservers()
        setupListeners()
        navController = findNavController()
//        confirm.isEnabled = true
//
//        code.setText(args.password)
//
//        confirm.setOnClickListener {
//            viewModel.confirm(args.phone, code.text.toString())
//        }

        updateNextButtonState()
    }

    private fun setupNumberPicker() {
        seatsNumberPicker.maxValue = 8
        seatsNumberPicker.minValue = 1

        passengerCount = seatsNumberPicker.value
        seatsNumberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            passengerCount = newVal
        }
    }


    @ExperimentalSplittiesApi
    private fun setupListeners() {
        navNext.setOnClickListener {
            activityViewModel.price = priceInput.text.toString().toInt()
            activityViewModel.seat = seatsNumberPicker.value
            navController.navigate(if (args.ISFROMPOSTPREVIEW) R.id.action_priceAndSeatFragment_to_previewFragment else R.id.action_priceAndSeatFragment_to_carAndTextFragment)
        }
        navBack.setOnClickListener {
            requireActivity().onBackPressed()
        }


        priceInput.doOnTextChanged { text, start, before, count ->
            updateNextButtonState()
        }

        priceInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                priceInputLayout.clearFocus()
                priceInput.clearFocus()
                priceInput.hideKeyboard()
            }
            true
        }

    }

    private fun updateNextButtonState() {
        navNext.isEnabled = !priceInput.text.isNullOrBlank()

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

    @ExperimentalSplittiesApi
    private fun setupObservers() {
//        viewModel.fromPlacesResponse.observe(viewLifecycleOwner, Observer {
//            val response = it ?: return@Observer
//
//            when (response) {
//                is ErrorWrapper.ResponseError -> {
//
//                }
//                is ErrorWrapper.SystemError -> {
//
//                }
//                is ResultWrapper.Success -> {
//                    fromAutocompletePresenter.getAdr().clear()
//                    response.value.forEach { place ->
//                        fromAutocompletePresenter.getAdr().add(PlaceAutocompleteItemView(place,
//                                                                                        fromAutocompletePresenter))
//
//                    }
//                    fromAutocompletePresenter.getAdr().notifyDataSetChanged()
//                }
//                ResultWrapper.InProgress -> {
//                    if (fromAutocompletePresenter.getAdr().itemCount == 0 || fromAutocompletePresenter.getAdr().getItem(
//                            fromAutocompletePresenter.getAdr().itemCount - 1) !is LoadingItemSmall) {
//                        fromAutocompletePresenter.getAdr().add(LoadingItemSmall())
//                        fromAutocompletePresenter.getAdr().notifyDataSetChanged()
//                    } else {
//
//                    }
//                }
//            }.exhaustive
//
//        })

    }


    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
//        (activity as AddPostActivity).showActionBar()
    }


}




