package com.badcompany.pitak.ui.main.searchtrip

import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.CalendarView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.PassengerPost
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.main.MainActivity
import com.badcompany.pitak.ui.viewgroups.LoadingItemSmall
import com.badcompany.pitak.ui.viewgroups.PassengerPostItem
import com.badcompany.pitak.ui.viewgroups.PlaceFeedItemView
import com.badcompany.pitak.util.AutoCompleteManager
import com.badcompany.pitak.util.buildAutoCompleteManager
import com.google.android.material.snackbar.Snackbar
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_trip.*
import splitties.experimental.ExperimentalSplittiesApi
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ExperimentalSplittiesApi
@AndroidEntryPoint
class SearchTripFragment @Inject constructor(/*private val viewModelFactory: ViewModelProvider.Factory*/) :
    Fragment(R.layout.fragment_search_trip) {

    private lateinit var balloon: Balloon
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel: SearchTripViewModel by viewModels() /*{
        viewModelFactory
    }*/

    lateinit var autoCompleteManager: AutoCompleteManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).hideTabLayout()
        setupAutoCompleteViews()
        setupListeners()
        setupRecyclerView()
        viewModel.getPassengerPost()
        subscribeObservers()
        lblPriceRange.text =
            getString(R.string.price_range) + " " + range_slider.getThumb(0).value + " - " + range_slider.getThumb(
                1).value

        setupDateBalloon()
    }

    private fun setupDateBalloon() {
        viewModel.filter.departureDate = SimpleDateFormat("dd.MM.yyyy").format(Date())
        date.text = getString(R.string.today)

        balloon = Balloon.Builder(requireContext())
            .setLayout(R.layout.layout_calendar)
            .setArrowSize(10)
            .setArrowOrientation(ArrowOrientation.LEFT)
            .setArrowPosition(0.5f)
            .setWidthRatio(0.7f)
            .setHeight(360)
            .setCornerRadius(4f)
            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setBalloonAnimation(BalloonAnimation.CIRCULAR)
            .setLifecycleOwner(viewLifecycleOwner)
            .build()

        val calendar = balloon.getContentView().findViewById<CalendarView>(R.id.calendar)
        val cal = Calendar.getInstance()

        calendar.minDate = cal.timeInMillis

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            viewModel.filter.departureDate = "$dayOfMonth.$month.$year"
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            date.text = getFormattedDate(cal.timeInMillis)
            balloon.dismiss()
            viewModel.getPassengerPost()
        }
    }

    fun getFormattedDate(smsTimeInMilis: Long): String {
        val smsTime = Calendar.getInstance()
        smsTime.timeInMillis = smsTimeInMilis
        val now = Calendar.getInstance()
        val dateTimeFormatString = "dd.MM.yyyy"
        return when {
            now[Calendar.DATE] == smsTime[Calendar.DATE] -> {
                getString(R.string.today)
            }
            now[Calendar.DATE] - smsTime[Calendar.DATE] == -1 -> {
                getString(R.string.tomorrow)

            }
            else -> {
                DateFormat.format(dateTimeFormatString, Date(smsTimeInMilis)).toString()
            }
        }
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
//                updateNextButtonState()
            }

            popUpClickAction { isFrom, item ->
                if (isFrom) {
                    viewModel.filter.fromRegionId = item.place.regionId
                    viewModel.filter.fromDistrictId = item.place.districtId
                } else {
                    viewModel.filter.toRegionId = item.place.regionId
                    viewModel.filter.toDistrictId = item.place.districtId
                }
                viewModel.getPassengerPost()
            }
        }
    }

    private fun setupListeners() {
        filterBtn.setOnClickListener {
            slidingLayer.openLayer(true)
        }
        applyFilter.setOnClickListener {

//            viewModel.filter.maxPrice = range_slider.getThumb(0).value
//            viewModel.filter.minPrice = range_slider.getThumb(1).value
            slidingLayer.closeLayer(true)
            checkAppliedFiltersCount()
            viewModel.getPassengerPost()
        }

        range_slider.setOnThumbValueChangeListener { multiSlider, thumb, thumbIndex, value ->
            lblPriceRange.text =
                getString(R.string.price_range) + " " + range_slider.getThumb(0).value + " - " + range_slider.getThumb(
                    1).value


        }

        resetFilter.setOnClickListener {
            slidingLayer.closeLayer(true)
            viewModel.resetFilter()
            viewModel.getPassengerPost()
            filter_count.visibility = View.INVISIBLE
            filter_count.text = ""
        }

        aCCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.filter.airConditioner = isChecked
        }

        date.setOnClickListener {
            balloon.showAlignRight(date)
            slidingLayer.closeLayer(true)
        }

    }

    private fun checkAppliedFiltersCount() {
        var count = 1
        if (range_slider.getThumb(0).value == 10000 && range_slider.getThumb(1).value == 1000000) {
            viewModel.filter.minPrice = null
            viewModel.filter.maxPrice = null
        } else {
            viewModel.filter.minPrice = range_slider.getThumb(0).value
            viewModel.filter.maxPrice = range_slider.getThumb(1).value
            count++
        }

        if (aCCheck.isChecked) count++

        if (timeFirstPart.isChecked || timeSecondPart.isChecked || timeThirdPart.isChecked || timeFourthPart.isChecked) count++


        if (count > 0) {
            filter_count.visibility = View.VISIBLE
            filter_count.text = count.toString()
        } else {

            filter_count.visibility = View.INVISIBLE
            filter_count.text = "0"
        }
    }

    private fun subscribeObservers() {
        viewModel.fromPlacesResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.RespError -> {
                }
                is ErrorWrapper.SystemError -> {
                }
                is ResultWrapper.Success -> {
                    autoCompleteManager.fromPresenter.getAdr()!!.clear()
                    response.value.forEach { place ->
                        autoCompleteManager.fromPresenter.getAdr()!!
                            .add(PlaceFeedItemView(place,
                                                   autoCompleteManager.fromPresenter))

                    }
                    autoCompleteManager.fromPresenter.getAdr()!!.notifyDataSetChanged()
                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive

        })

        viewModel.toPlacesResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.RespError -> {
                }
                is ErrorWrapper.SystemError -> {
                }
                is ResultWrapper.Success -> {
                    if (autoCompleteManager.toPresenter.getAdr() != null) {
                        autoCompleteManager.toPresenter.getAdr()!!.clear()
                        response.value.forEach { place ->
                            autoCompleteManager.toPresenter.getAdr()!!
                                .add(PlaceFeedItemView(place,
                                                       autoCompleteManager.toPresenter))
                        }
                        autoCompleteManager.toPresenter.getAdr()!!
                            .notifyDataSetChanged()
                    } else {
                    }
                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive

        })

        viewModel.passengerPostsReponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.RespError -> {
                    Snackbar.make(nestedScroll,
                                  response.message!!,
                                  Snackbar.LENGTH_SHORT).show()

                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(nestedScroll,
                                  response.err.localizedMessage.toString(),
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {
                    loadData(response.value)
                }
                ResultWrapper.InProgress -> {
                    addLoading()
                }
            }.exhaustive
        })

    }

    private fun setupRecyclerView() {
        passengerPosts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        passengerPosts.setHasFixedSize(true)
        passengerPosts.adapter = adapter
    }

    private fun addLoading() {
        adapter.clear()
        adapter.add(LoadingItemSmall())
        adapter.notifyDataSetChanged()
    }

    private fun loadData(value: List<PassengerPost>) {
        adapter.clear()

        if (value.isEmpty()) {
            infoText.visibility = View.VISIBLE
            infoText.text = getString(R.string.no_posts_found)
        } else {
            infoText.visibility = View.INVISIBLE
            value.forEach {
                adapter.add(PassengerPostItem(it))
            }
        }
        adapter.notifyDataSetChanged()

    }


}