package com.novatec.epitak.ui.bsd_destination

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.Place
import com.novatec.epitak.R
import com.novatec.epitak.ui.addpost.destinations.DestinationsViewModel
import com.novatec.epitak.ui.viewgroups.ItemDestination
import com.novatec.epitak.util.BSDExpanded
import com.novatec.epitak.viewobjects.PlaceViewObj
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bsd_destination.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val ARG_IS_FROM = "IS_FROM"
const val REQ_DESTINATION = "DESTINATION"
const val RESULT_PLACE = "PLACE"
private const val MIN_QUERY_CHARS: Int = 3


@AndroidEntryPoint
class DestinationBSD : BSDExpanded() {

    override fun getTheme() = R.style.BottomSheetExpanded

    private var isFrom: Boolean = true
    private val viewModel: DestinationsViewModel by viewModels()

    val adapter = GroupAdapter<GroupieViewHolder>()
    override fun onAttach(context: Context) {
        super.onAttach(context)

        isFrom = arguments?.getBoolean(ARG_IS_FROM) ?: true

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bsd_destination, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        attachListeners()
        subscribeObservers()
    }

    private fun setup() {
        lblDestination.text = getString(if (isFrom) R.string.from else R.string.to)
        rvDestinations.adapter = adapter
    }

    private fun subscribeObservers() {

        viewModel.placesResponse.observe(viewLifecycleOwner) {
            val resp = it ?: return@observe
            when (resp) {
                is ErrorWrapper.RespError -> {
                    progress.isVisible = false
                    rvDestinations.isVisible = false
                    tvInfo.isVisible = true
                    tvInfo.text = resp.message ?: getString(R.string.system_error)
                }
                is ErrorWrapper.SystemError -> {
                    progress.isVisible = false
                    rvDestinations.isVisible = false
                    tvInfo.isVisible = true
                    tvInfo.text = resp.err.localizedMessage ?: getString(R.string.system_error)
                }
                ResultWrapper.InProgress -> {
                    progress.isVisible = true
                    tvInfo.isVisible = false
                    rvDestinations.isVisible = false

                }
                is ResultWrapper.Success -> {
                    progress.isVisible = false
                    showData(resp.value)
//                            setFragmentResult(RESULT_PLACE, )
                }
            }
        }
    }

    private fun showData(value: List<Place>) {
        adapter.clear()
        if (value.isEmpty()) {
            rvDestinations.isVisible = false
            tvInfo.isVisible = true
            tvInfo.text = getString(R.string.no_destination_found)
        } else {
            rvDestinations.isVisible = true
            tvInfo.isVisible = false
            viewModel.preSelectedPlace = null
            value.forEachIndexed { index, place ->
                if (index == 0) viewModel.preSelectedPlace = place
                adapter.add(ItemDestination(place) {
                    setFragmentResult(REQ_DESTINATION, Bundle().apply {
                        putParcelable(RESULT_PLACE, PlaceViewObj.fromPlace(it))
                    })
                    dismiss()
                })
            }
        }

    }


    var queryJob: Job? = null

    private fun attachListeners() {
        ivClear.setOnClickListener {
            edtSearch.setText("")
            ivClear.visibility = View.GONE
        }

        edtSearch.doOnTextChanged { text, start, before, count ->
            ivClear.visibility = if (!text.isNullOrBlank()) View.VISIBLE else View.GONE
        }

        edtSearch.doOnTextChanged { text, start, before, count ->
            text?.let { queryString ->
                if (queryString.length >= MIN_QUERY_CHARS) {
                    queryJob?.cancel()
                    queryJob = lifecycleScope.launch {
                        delay(1000)
                        viewModel.getPlacesFeed(queryString.toString(), isFrom)
                    }
                }
            }

        }

        edtSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                viewModel.preSelectedPlace?.let {
                    setFragmentResult(REQ_DESTINATION, Bundle().apply {
                        putParcelable(RESULT_PLACE, PlaceViewObj.fromPlace(it))
                    })
                    dismiss()
                }
                return@OnEditorActionListener true
            }
            false
        })

    }
}