package com.novatec.epitak.ui.addpost.priceandseat

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
import com.novatec.epitak.R
import com.novatec.epitak.ui.addpost.AddPostViewModel
import com.novatec.epitak.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_price_and_seat.*
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject


@AndroidEntryPoint
class PriceAndSeatFragment :    Fragment(R.layout.fragment_price_and_seat) {

    val args: PriceAndSeatFragmentArgs by navArgs()
    private var passengerCount: Int? = null
    private val activityViewModel: AddPostViewModel by activityViewModels()
    lateinit var navController: NavController


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


    }



}




