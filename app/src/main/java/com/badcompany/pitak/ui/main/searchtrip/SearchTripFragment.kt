package com.badcompany.pitak.ui.main.searchtrip

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.MAX_PRICE
import com.badcompany.domain.domainmodel.MIN_PRICE
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.main.MainActivity
import com.badcompany.pitak.ui.viewgroups.PlaceFeedItemView
import com.badcompany.pitak.util.AutoCompleteManager
import com.badcompany.pitak.util.DateUtils.getFormattedDate
import com.badcompany.pitak.util.buildAutoCompleteManager
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_trip.*
import splitties.experimental.ExperimentalSplittiesApi
import java.util.*

@ExperimentalSplittiesApi
@AndroidEntryPoint
class SearchTripFragment : Fragment(R.layout.fragment_search_trip) {

    private lateinit var balloon: Balloon
    private val viewModel: SearchTripViewModel by viewModels()

    lateinit var autoCompleteManager: AutoCompleteManager
    var postsAdapter = PostFilterAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).hideTabLayout()
        setupListeners()
        setupViews()
        viewModel.getPassengerPost()
        subscribeObservers()
        setupDateBalloon()
    }

    private fun setupDateBalloon() {

        date.text = getString(R.string.anytime)

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

            viewModel.setDate(dayOfMonth, month, year)
            val calTemp = Calendar.getInstance()
            calTemp.set(year, month, dayOfMonth)
            date.text = getFormattedDate(calTemp.timeInMillis, requireContext())
            balloon.dismiss()

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
            updateBtnStateAction {
            }

            popUpClickAction { isFrom, item ->
                if (isFrom) viewModel.placeFromSelected(item.place)
                else viewModel.placeToSelected(item.place)

            }
        }
    }

    private fun setupListeners() {
        filterBtn.setOnClickListener {
            slidingLayer.openLayer(true)
        }
        applyFilter.setOnClickListener {
            viewModel.applyFilter()
            slidingLayer.closeLayer(true)
        }

        range_slider.setOnThumbValueChangeListener { multiSlider, thumb, thumbIndex, value ->
            lblPriceRange.text =
                "${getString(R.string.price_range)} ${range_slider.getThumb(0).value} - ${
                    range_slider.getThumb(1).value
                }"
            viewModel.setFilterPrices(range_slider.getThumb(0).value,
                                      range_slider.getThumb(1).value)
        }

        resetFilter.setOnClickListener {
            slidingLayer.closeLayer(true)
            viewModel.resetFilter()
            date.text = getString(R.string.anytime)

            filter_count.visibility = View.INVISIBLE
            filter_count.text = ""
            timeFirstPart.isChecked = false
            timeSecondPart.isChecked = false
            timeThirdPart.isChecked = false
            timeFourthPart.isChecked = false
            aCCheck.isChecked = false
            range_slider.min = MIN_PRICE
            range_slider.max = MAX_PRICE
            number_picker.resetText()
        }

        aCCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.filterAC(isChecked)
        }

        date.setOnClickListener {
            balloon.showAlignRight(date)
            slidingLayer.closeLayer(true)
        }

        timeFirstPart.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.dayTimePartsChecked(timeFirstPart.isChecked,
                                          timeSecondPart.isChecked,
                                          timeThirdPart.isChecked,
                                          timeFourthPart.isChecked)
        }
        timeSecondPart.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.dayTimePartsChecked(timeFirstPart.isChecked,
                                          timeSecondPart.isChecked,
                                          timeThirdPart.isChecked,
                                          timeFourthPart.isChecked)
        }
        timeThirdPart.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.dayTimePartsChecked(timeFirstPart.isChecked,
                                          timeSecondPart.isChecked,
                                          timeThirdPart.isChecked,
                                          timeFourthPart.isChecked)
        }
        timeFourthPart.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.dayTimePartsChecked(timeFirstPart.isChecked,
                                          timeSecondPart.isChecked,
                                          timeThirdPart.isChecked,
                                          timeFourthPart.isChecked)
        }

        number_picker.addOnSeatCountChangeListener { count ->
            viewModel.seatCountChanged(count)
        }


    }


    private fun subscribeObservers() {

        viewModel.filter.observe(viewLifecycleOwner, {
            postsAdapter.refresh()
        })

        viewModel.count.observe(viewLifecycleOwner, { count ->
            if (count > 0) {
                filter_count.visibility = View.VISIBLE
                filter_count.text = count.toString()
            } else {
                filter_count.visibility = View.INVISIBLE
                filter_count.text = "0"
            }
        })


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

        viewModel.postOffers.observe(viewLifecycleOwner, {
            val value = it ?: return@observe
            postsAdapter.submitData(lifecycle, value)
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

    }

    private fun setupViews() {
        motionLayout.getTransition(R.id.search_trip_panel_trans).setEnable(false)
        setupAutoCompleteViews()

        rvPosts.adapter = postsAdapter.withLoadStateHeaderAndFooter(
            header = PostLoadStateAdapter { postsAdapter.retry() },
            footer = PostLoadStateAdapter { postsAdapter.retry() }
        )

        postsAdapter.addLoadStateListener { loadState ->
            progress.isVisible = loadState.source.refresh is LoadState.Loading
            rvPosts.isVisible = loadState.source.refresh is LoadState.NotLoading
            tv_error.isVisible = loadState.source.refresh is LoadState.Error
            if (loadState.source.refresh is LoadState.Error) {
                tv_error.text = (loadState.source.refresh as LoadState.Error).error.localizedMessage
            }
            btn_retry.isVisible = loadState.source.refresh is LoadState.Error

            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && postsAdapter.itemCount < 1) {
                rvPosts.isVisible = false
                tv_error.isVisible = true
                tv_error.setText(R.string.there_are_no_posts_yet_come_back_later)
                motionLayout.getTransition(R.id.search_trip_panel_trans).setEnable(false)

            } else if (loadState.source.refresh !is LoadState.Error) {
                rvPosts.isVisible = true
                tv_error.isVisible = false
                motionLayout.getTransition(R.id.search_trip_panel_trans).setEnable(true)
            }
        }

        lblPriceRange.text =
            getString(R.string.price_range) + " " + range_slider.getThumb(0).value + " - " + range_slider.getThumb(
                1).value
    }


    override fun onDestroyView() {
        super.onDestroyView()

        autoCompleteManager.dispose()
    }
}