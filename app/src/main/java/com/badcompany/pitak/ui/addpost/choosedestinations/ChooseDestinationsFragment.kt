package com.badcompany.pitak.ui.addpost.choosedestinations

import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.`interface`.IOnPlaceSearchQueryListener
import com.badcompany.pitak.ui.viewgroups.PlaceAutocompleteItemView
import com.otaliastudios.autocomplete.Autocomplete
import com.otaliastudios.autocomplete.AutocompleteCallback
import com.otaliastudios.autocomplete.AutocompletePolicy
import kotlinx.android.synthetic.main.fragment_choose_destinations.*
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject


//@FlowPreview
//@ExperimentalCoroutinesApi
class ChooseDestinationsFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    Fragment(R.layout.fragment_choose_destinations) {

    private var fromAutocomplete: Autocomplete<PlaceAutocompleteItemView>? = null
    private val viewModel: ChooseDestinationsViewModel by viewModels {
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
        setupListeners()

//        navController = findNavController()
//        confirm.isEnabled = true
//
//        code.setText(args.password)
//
//        confirm.setOnClickListener {
//            viewModel.confirm(args.phone, code.text.toString())
//        }


    }


    private fun setupListeners() {
        fromAutocomplete = Autocomplete.on<PlaceAutocompleteItemView>(fromInput)
            .with(object : AutocompletePolicy {
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

            })
            .with(object : AutocompleteCallback<PlaceAutocompleteItemView> {
                override fun onPopupItemClicked(editable: Editable?,
                                                item: PlaceAutocompleteItemView): Boolean {
                    return true
                }

                override fun onPopupVisibilityChanged(shown: Boolean) {

                }

            })
            .with(DestinationAutocompletePresenter(requireContext(), fromQueryListener))
//            .with(popupBackground)
            .with(3f)
            .build()


    }


    private val fromQueryListener = object : IOnPlaceSearchQueryListener {
        override fun onQuery(query: CharSequence?) {
            if (query!!.length % 3 == 0) viewModel.makeFromSearch(fromInput.text.toString())

        }
    }

    @ExperimentalSplittiesApi
    private fun setupObservers() {
//        viewModel.confirmResponse.observe(viewLifecycleOwner, Observer {
//            val response = it ?: return@Observer
//
//            when (response) {
//                is ErrorWrapper.ResponseError -> {
//                    confirm.revertAnimation()
//                    if (response.code == Constants.errPhoneFormat) {
//                        code.error = getString(R.string.incorrect_phone_number_format)
////                        errorMessage.visibility = View.VISIBLE
////                        errorMessage.text = response.message
//                    } else {
//                        errorMessage.visibility = View.VISIBLE
//                        errorMessage.text = response.message
//                    }
//                }
//                is ErrorWrapper.SystemError -> {
//                    errorMessage.visibility = View.VISIBLE
//                    errorMessage.text = response.err.message
//                    confirm.revertAnimation()
//                }
//                is ResultWrapper.Success -> {
//                    confirm.revertAnimation()
//                    saveCredentials(response)
//                    context?.start<MainActivity> { }
//
//                    (appCtx as App).releaseAuthComponent()
//                }
//                ResultWrapper.InProgress -> {
//                    errorMessage.visibility = View.INVISIBLE
//                    confirm.startAnimation()
//                }
//            }.exhaustive
//
//        })
    }


    override fun onResume() {
        super.onResume()
//        (activity as AddPostActivity).showActionBar()
    }


}




