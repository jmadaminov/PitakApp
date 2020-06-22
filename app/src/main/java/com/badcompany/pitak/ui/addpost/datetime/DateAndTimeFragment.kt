package com.badcompany.pitak.ui.addpost.datetime

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.badcompany.domain.domainmodel.Place
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.addpost.AddPostViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_date_and_time.*
import org.joda.time.DateTime
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject


//@FlowPreview
//@ExperimentalCoroutinesApi
class DateAndTimeFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    Fragment(R.layout.fragment_date_and_time) {


    private var placeFrom: Place? = null
    private var placeTo: Place? = null

    private val activityViewModel: AddPostViewModel by activityViewModels {
        viewModelFactory
    }

    //    val args: PhoneConfirmFragmentArgs by navArgs()
    lateinit var navController: NavController

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()

        navController = findNavController()
        calendar.state().edit().setMinimumDate(CalendarDay.today()).commit()
        calendar.selectedDate = CalendarDay.today()

    }


    @ExperimentalSplittiesApi
    private fun setupListeners() {
        asapCheck.setOnCheckedChangeListener { buttonView, isChecked ->

            checkFirstPartDay.isEnabled = !isChecked
            checkFirstPartNight.isEnabled = !isChecked
            checkSecondPartDay.isEnabled = !isChecked
            checkSecondPartNight.isEnabled = !isChecked

            checkFirstPartDay.isChecked = isChecked
            checkFirstPartNight.isChecked = isChecked
            checkSecondPartDay.isChecked = isChecked
            checkSecondPartNight.isChecked = isChecked

            if (isChecked) {
                calendar.selectedDate = CalendarDay.today()
                calendar.state().edit()
                    .setMinimumDate(CalendarDay.today())
                    .setMaximumDate(CalendarDay.today())
                    .commit()

            } else {
                val todayPlusThreeMonths = DateTime.now().plusMonths(3)
                calendar.state().edit().setMinimumDate(CalendarDay.today())
                    .setMaximumDate(CalendarDay.from(todayPlusThreeMonths.year,
                                                     todayPlusThreeMonths.monthOfYear,
                                                     todayPlusThreeMonths.dayOfMonth)).commit()
            }

        }



        navNext.setOnClickListener {
            navController.navigate(R.id.action_dateTimeFragment_to_priceAndSeatFragment)
        }

        navBack.setOnClickListener {
            requireActivity().onBackPressed()
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


}




