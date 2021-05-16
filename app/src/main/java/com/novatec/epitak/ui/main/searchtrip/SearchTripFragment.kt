package com.novatec.epitak.ui.main.searchtrip

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.core.exhaustive
import com.novatec.epitak.R
import com.novatec.epitak.ui.main.MainActivity
import com.novatec.epitak.ui.viewgroups.PlaceFeedItemView
import com.novatec.epitak.util.AutoCompleteManager
import com.novatec.epitak.util.DateUtils.getFormattedDate
import com.novatec.epitak.util.buildAutoCompleteManager
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
        attachListeners()
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

    private fun attachListeners() {
        ivClearFrom.setOnClickListener {
            fromInput.setText("")
            ivClearFrom.visibility = View.GONE
            fromInput.clearFocus()
        }

        ivClearTo.setOnClickListener {
            toInput.setText("")
            ivClearTo.visibility = View.GONE
            toInput.clearFocus()
        }

        fromInput.doOnTextChanged { text, start, before, count ->
            ivClearFrom.visibility = if (!text.isNullOrBlank()) View.VISIBLE else View.GONE
        }

        toInput.doOnTextChanged { text, start, before, count ->
            ivClearTo.visibility = if (!text.isNullOrBlank()) View.VISIBLE else View.GONE
        }

        btn_retry.setOnClickListener { postsAdapter.refresh() }

        fromInput.setOnFocusChangeListener { v, hasFocus ->
            val drawable =
                DrawableCompat.wrap(resources.getDrawable(R.drawable.ic_round_my_location_24))
            if (hasFocus) {
                DrawableCompat.setTint(drawable,
                                       ContextCompat.getColor(requireContext(),
                                                              R.color.colorAccent))
            } else {
                DrawableCompat.setTint(drawable,
                                       ContextCompat.getColor(requireContext(), R.color.ic_grey))
            }
            DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_ATOP)
            fromInput.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        }

        toInput.setOnFocusChangeListener { v, hasFocus ->
            val drawable =
                DrawableCompat.wrap(resources.getDrawable(R.drawable.ic_round_location_on_24))
            if (hasFocus) {
                DrawableCompat.setTint(drawable,
                                       ContextCompat.getColor(requireContext(),
                                                              R.color.colorAccent))
            } else {
                DrawableCompat.setTint(drawable,
                                       ContextCompat.getColor(requireContext(), R.color.ic_grey))
            }
            DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_ATOP)
            toInput.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        }

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
                    autoCompleteManager.fromPresenter.getAdr()?.let { adapter ->
                        adapter.clear()
                        response.value.forEach { place ->
                            adapter.add(PlaceFeedItemView(place, autoCompleteManager.fromPresenter))
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
                ResultWrapper.InProgress -> {
                }
            }

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
                    autoCompleteManager.toPresenter.getAdr()?.let { adapter ->
                        adapter.clear()
                        response.value.forEach { place ->
                            adapter.add(PlaceFeedItemView(place, autoCompleteManager.toPresenter))
                        }
                        adapter.notifyDataSetChanged()
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
            if (progress == null) return@addLoadStateListener
//            swipeRefreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading
            progress.isVisible = loadState.source.refresh is LoadState.Loading
            rvPosts.isVisible = loadState.source.refresh is LoadState.NotLoading
            if (loadState.source.refresh is LoadState.Error) {
                tv_error.isVisible = true
                tv_error.text = (loadState.source.refresh as LoadState.Error).error.localizedMessage
                btn_retry.isVisible = true
                motionLayout.getTransition(R.id.search_trip_panel_trans).setEnable(false)
            } else if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && postsAdapter.itemCount < 1) {
                rvPosts.isVisible = false
                infoText.isVisible = true
                infoText.setText(R.string.there_are_no_posts_yet_come_back_later)
                motionLayout.getTransition(R.id.search_trip_panel_trans).setEnable(false)
                btn_retry.isVisible = false

            } else if (loadState.source.refresh !is LoadState.Error) {
                btn_retry.isVisible = false
                rvPosts.isVisible = true
                tv_error.isVisible = false
                infoText.isVisible = false
                motionLayout.getTransition(R.id.search_trip_panel_trans).setEnable(true)
            }
        }

        lblPriceRange.text =
            getString(R.string.price_range) + " " + range_slider.getThumb(0).value + " - " + range_slider.getThumb(
                1).value
    }


    override fun onResume() {
        super.onResume()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        postsAdapter.removeLoadStateListener { }
        autoCompleteManager.dispose()
    }


}