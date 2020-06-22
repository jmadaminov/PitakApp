package com.badcompany.pitak.ui.addpost.destinations

import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.pitak.MapsActivity
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.`interface`.IOnPlaceSearchQueryListener
import com.badcompany.pitak.ui.viewgroups.PlaceAutocompleteItemView
import com.otaliastudios.autocomplete.Autocomplete
import com.otaliastudios.autocomplete.AutocompleteCallback
import com.otaliastudios.autocomplete.AutocompletePolicy
import kotlinx.android.synthetic.main.fragment_destinations.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.start
import javax.inject.Inject


//@FlowPreview
//@ExperimentalCoroutinesApi
class DestinationsFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    Fragment(R.layout.fragment_destinations) {


    private var fromAutocomplete: Autocomplete<PlaceAutocompleteItemView>? = null
    private var toAutocomplete: Autocomplete<PlaceAutocompleteItemView>? = null
    private val viewModel: DestinationsViewModel by viewModels {
        viewModelFactory
    }

    //    val args: PhoneConfirmFragmentArgs by navArgs()
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupFromInputAutocomplete()
        setupToInputAutocomplete()
        setupListeners()

        navController = findNavController()
//        confirm.isEnabled = true
//
//        code.setText(args.password)
//
//        confirm.setOnClickListener {
//            viewModel.confirm(args.phone, code.text.toString())
//        }


    }


    @ExperimentalSplittiesApi
    private fun setupListeners() {

        next.setOnClickListener {
            navController.navigate(R.id.action_destinationsFragment_to_dateTimeFragment)
        }

        fromInput.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus -> if (!hasFocus) fromAutocomplete?.dismissPopup() }
        toInput.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus -> if (!hasFocus) toAutocomplete?.dismissPopup() }

        map.setOnClickListener {
            start<MapsActivity>()
        }

    }

    @ExperimentalSplittiesApi
    private fun setupFromInputAutocomplete() {
        fromAutocompletePresenter =
            DestinationAutocompletePresenter(requireContext(), autoCompleteQueryListener)
        fromAutocomplete = Autocomplete.on<PlaceAutocompleteItemView>(fromInput)
            .with(autoCompletePolicy)
            .with(fromAutocompleteCallback)
            .with(fromAutocompletePresenter)
            .with(requireContext().getDrawable(R.drawable.selector_rounded_corners))
            .with(3f)
            .build()
        fromAutocomplete!!.setGravity(Gravity.BOTTOM)
    }

    @ExperimentalSplittiesApi
    private fun setupToInputAutocomplete() {
        toAutocompletePresenter =
            DestinationAutocompletePresenter(requireContext(), autoCompleteQueryListener, false)

        toAutocomplete = Autocomplete.on<PlaceAutocompleteItemView>(toInput)
            .with(autoCompletePolicy)
            .with(toAutocompleteCallback)
            .with(toAutocompletePresenter)
            .with(requireContext().getDrawable(R.drawable.selector_rounded_corners))
            .with(3f)
            .build()
        toAutocomplete!!.setGravity(Gravity.END)
    }


    @ExperimentalSplittiesApi
    private fun setupObservers() {
        viewModel.fromPlacesResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.ResponseError -> {

                }
                is ErrorWrapper.SystemError -> {

                }
                is ResultWrapper.Success -> {
                    fromAutocompletePresenter.getAdr().clear()
                    response.value.forEach { place ->
                        fromAutocompletePresenter.getAdr().add(PlaceAutocompleteItemView(place,
                                                                                         fromAutocompletePresenter))

                    }
                    fromAutocompletePresenter.getAdr().notifyDataSetChanged()
                }
                ResultWrapper.InProgress -> {
//                    if (fromAutocompletePresenter.getAdr().itemCount == 0 || fromAutocompletePresenter.getAdr().getItem(
//                            fromAutocompletePresenter.getAdr().itemCount - 1) !is LoadingItemSmall) {
//                        fromAutocompletePresenter.getAdr().add(LoadingItemSmall())
//                        fromAutocompletePresenter.getAdr().notifyDataSetChanged()
//                    } else {
//
//                    }
                }
            }.exhaustive

        })
        viewModel.toPlacesResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.ResponseError -> {

                }
                is ErrorWrapper.SystemError -> {

                }
                is ResultWrapper.Success -> {
                    toAutocompletePresenter.getAdr().clear()
                    response.value.forEach { place ->
                        toAutocompletePresenter.getAdr().add(PlaceAutocompleteItemView(place,
                                                                                       toAutocompletePresenter))

                    }
                    toAutocompletePresenter.getAdr().notifyDataSetChanged()
                }
                ResultWrapper.InProgress -> {
//                    if (toAutocompletePresenter.getAdr().itemCount == 0 || toAutocompletePresenter.getAdr().getItem(
//                            toAutocompletePresenter.getAdr().itemCount - 1) !is LoadingItemSmall) {
//                        toAutocompletePresenter.getAdr().add(LoadingItemSmall())
//                        toAutocompletePresenter.getAdr().notifyDataSetChanged()
//                    } else {
//
//                    }
                }
            }.exhaustive

        })
    }


    override fun onPause() {
        super.onPause()
        fromAutocomplete?.dismissPopup()
        toAutocomplete?.dismissPopup()
    }

    override fun onResume() {
        super.onResume()
//        (activity as AddPostActivity).showActionBar()
    }


    @ExperimentalSplittiesApi
    private val autoCompleteQueryListener = object : IOnPlaceSearchQueryListener {
        override fun onQuery(query: CharSequence?, isFrom: Boolean) {
            if (query!!.length % 3 == 0) viewModel.getPlacesFeed(query.toString(), isFrom)
        }
    }

    private val fromAutocompleteCallback =
        object : AutocompleteCallback<PlaceAutocompleteItemView> {
            override fun onPopupItemClicked(editable: Editable,
                                            item: PlaceAutocompleteItemView): Boolean {
                editable.clear()
                editable.insert(0, item.place.nameUz)
                fromInput.clearFocus()
                viewModel.placeFrom = item.place
                return true
            }

            override fun onPopupVisibilityChanged(shown: Boolean) {
            }
        }
    private val toAutocompleteCallback =
        object : AutocompleteCallback<PlaceAutocompleteItemView> {
            override fun onPopupItemClicked(editable: Editable,
                                            item: PlaceAutocompleteItemView): Boolean {
                editable.clear()
                editable.insert(0, item.place.nameUz)
                toInput.clearFocus()
                viewModel.placeTo = item.place
                updateNextButtonState()
                return true
            }

            override fun onPopupVisibilityChanged(shown: Boolean) {
            }
        }

    private fun updateNextButtonState() {
        next.isEnabled = viewModel.placeFrom != null && viewModel.placeTo != null

        if (next.isEnabled) {
            val bg = next.background
            bg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorAccent), PorterDuff.Mode.SRC_ATOP)
            next.background = bg
        } else {
            val bg = next.background
            bg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.ic_grey), PorterDuff.Mode.SRC_ATOP)
            next.background = bg
        }
    }

    @ExperimentalSplittiesApi
    private lateinit var fromAutocompletePresenter: DestinationAutocompletePresenter

    @ExperimentalSplittiesApi
    private lateinit var toAutocompletePresenter: DestinationAutocompletePresenter

    private val autoCompletePolicy = object : AutocompletePolicy {
        override fun shouldShowPopup(text: Spannable?, cursorPos: Int): Boolean {
            return text!!.length > 0
        }

        override fun shouldDismissPopup(text: Spannable?, cursorPos: Int): Boolean {
            return text!!.length == 0
        }

        override fun getQuery(text: Spannable?): CharSequence {
            return text!!
        }

        override fun onDismiss(text: Spannable?) {
        }

    }

}




