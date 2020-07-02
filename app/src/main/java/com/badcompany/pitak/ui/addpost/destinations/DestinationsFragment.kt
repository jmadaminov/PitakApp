package com.badcompany.pitak.ui.addpost.destinations

import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.pitak.MapsActivity
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.addpost.AddPostViewModel
import com.badcompany.pitak.ui.interfaces.IOnPlaceSearchQueryListener
import com.badcompany.pitak.ui.viewgroups.PlaceAutocompleteItemView
import com.badcompany.pitak.util.hideKeyboard
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

    val args: DestinationsFragmentArgs by navArgs()

    private var fromAutocomplete: Autocomplete<PlaceAutocompleteItemView>? = null
    private var toAutocomplete: Autocomplete<PlaceAutocompleteItemView>? = null
    private val viewModel: DestinationsViewModel by viewModels {
        viewModelFactory
    }
    private val activityViewModel: AddPostViewModel by activityViewModels {
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
        if (args.ISFROMPOSTPREVIEW) {
            viewModel.placeTo = activityViewModel.placeTo
            viewModel.placeFrom = activityViewModel.placeFrom
            fromInput.setText(viewModel.placeFrom!!.nameUz)
            toInput.setText(viewModel.placeTo!!.nameUz)
            navBack.visibility = View.VISIBLE
        } else {
            navBack.visibility = View.INVISIBLE
        }


        setupFromInputAutocomplete()
        setupToInputAutocomplete()
        setupListeners()
        updateNextButtonState()
        setupObservers()


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

        navBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        fromInput.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    fromAutocompleteCallback.onPopupItemClicked(fromInput.editableText,
                                                                fromAutocompletePresenter.getAdr()!!
                                                                    .getItem(0) as PlaceAutocompleteItemView)
                    toInput.requestFocus()
                }
            }
            false
        }
        toInput.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {

                    toAutocompleteCallback.onPopupItemClicked(toInput.editableText,
                                                              toAutocompletePresenter.getAdr()!!
                                                                  .getItem(0) as PlaceAutocompleteItemView)

                }
            }
            false
        }

        next.setOnClickListener {
            activityViewModel.placeFrom = viewModel.placeFrom
            activityViewModel.placeTo = viewModel.placeTo

            navController.navigate(if (args.ISFROMPOSTPREVIEW) R.id.action_destinationsFragment_to_previewFragment else R.id.action_destinationsFragment_to_dateTimeFragment)
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
            DestinationAutocompletePresenter(requireContext(), viewModel, autoCompleteQueryListener)
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
            DestinationAutocompletePresenter(requireContext(),
                                             viewModel,
                                             autoCompleteQueryListener,
                                             false)

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
                    fromAutocompletePresenter.getAdr()!!.clear()
                    response.value.forEach { place ->
                        fromAutocompletePresenter.getAdr()!!.add(PlaceAutocompleteItemView(place,
                                                                                           fromAutocompletePresenter))

                    }
                    fromAutocompletePresenter.getAdr()!!.notifyDataSetChanged()
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
                    toAutocompletePresenter.getAdr()!!.clear()
                    response.value.forEach { place ->
                        toAutocompletePresenter.getAdr()!!.add(PlaceAutocompleteItemView(place,
                                                                                         toAutocompletePresenter))

                    }
                    toAutocompletePresenter.getAdr()!!.notifyDataSetChanged()
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
        override fun onQuery(query: CharSequence?, isFrom: Boolean, isSelectedFromFeed: Boolean) {
            if (query!!.length % 3 == 0 && !isSelectedFromFeed) viewModel.getPlacesFeed(query.toString(),
                                                                                        isFrom)
        }
    }

    private val fromAutocompleteCallback =
        object : AutocompleteCallback<PlaceAutocompleteItemView> {
            override fun onPopupItemClicked(editable: Editable,
                                            item: PlaceAutocompleteItemView): Boolean {
                editable.clear()
                editable.insert(0, item.place.nameUz)
                fromInput.clearFocus()
                fromInput.hideKeyboard()
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
                toInput.hideKeyboard()
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
            bg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                              PorterDuff.Mode.SRC_ATOP)
            next.background = bg
        } else {
            val bg = next.background
            bg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.ic_grey),
                              PorterDuff.Mode.SRC_ATOP)
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




