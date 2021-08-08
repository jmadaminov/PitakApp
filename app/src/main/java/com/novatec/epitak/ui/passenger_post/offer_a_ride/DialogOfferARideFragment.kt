package com.novatec.epitak.ui.passenger_post.offer_a_ride

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.novatec.core.EPostType
import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.epitak.R
import com.novatec.epitak.ui.passenger_post.PassengerPostActivity
import com.novatec.epitak.ui.viewgroups.ItemActivePost
import com.novatec.epitak.util.BSDExpanded
import com.novatec.epitak.viewobjects.MyOfferViewObj
import com.novatec.epitak.viewobjects.PassengerPostViewObj
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_offer_a_ride.*
import splitties.experimental.ExperimentalSplittiesApi

const val ARG_PASSENGER_POST = "PASSENGER_POST"

@AndroidEntryPoint
class DialogOfferARideFragment : BSDExpanded() {
    private val adapter = GroupAdapter<GroupieViewHolder>()

    private lateinit var passengerPost: PassengerPostViewObj
    val viewModel: OfferARideViewModel by viewModels()

//    override fun getTheme() = R.style.Theme_Dialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        passengerPost = requireArguments().getParcelable(ARG_PASSENGER_POST)!!
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_offer_a_ride, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        edtPrice.hint =
//            DecimalFormat("#,###").format(passengerPost.price) + " " + getString(R.string.sum)
        rvMyPosts.adapter = adapter
        attachListeners()
        subscribeObservers()

        passengerPost.myLastOffer?.let { showOfferSent(passengerPost.myLastOffer!!) } ?: run {
            viewModel.getActivePosts()
        }

    }

    private fun showOfferSent(myLastOffer: MyOfferViewObj) {
        viewModel.offeringPostId.value = myLastOffer.repliedPostId
        rvContainer.isVisible = false
        lblSelectPost.isVisible = false
        cardLastOffer.isVisible = true
        tvLastOfferPrice.text = getString(R.string.price) + " " + myLastOffer.priceInt.toString()
        tvLastOfferRepliedPostId.text =
            getString(R.string.attached_post_id) + " " + myLastOffer.repliedPostId.toString()
        tvLastOfferMessage.text = getString(R.string.message) + " " + myLastOffer.message
        btnSendOffer.text = getString(R.string.update_offer)
    }

    private fun subscribeObservers() {

        viewModel.isOffering.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) btnSendOffer.startAnimation() else btnSendOffer.revertAnimation()
        })

        viewModel.hasFinished.observe(viewLifecycleOwner, { hasFinished ->
            (requireActivity() as PassengerPostActivity).refreshPost()
            if (hasFinished) dismiss()

        })

        viewModel.errorMessage.observe(viewLifecycleOwner, { message ->
            Snackbar.make(view?.rootView!!,
                          message,
                          Snackbar.LENGTH_SHORT).show()

        })

        viewModel.activePostsResponse.observe(viewLifecycleOwner, {
            val response = it ?: return@observe
            when (response) {
                is ErrorWrapper.RespError -> {
                }
                is ErrorWrapper.SystemError -> {
                }
                is ResultWrapper.Success -> {
                    loadData(response.value.filter { myPost ->
                        myPost.departureDate == passengerPost.departureDate
                                && (myPost.postType == EPostType.DRIVER_SM && myPost.postStatus!!.canTakePassenger()
                                || myPost.postType == EPostType.DRIVER_PARCEL && myPost.postStatus!!.canTakeParcel())
                    })
                }
                ResultWrapper.InProgress -> {
                }
            }
        })

    }

    private fun attachListeners() {

        edtPrice.doOnTextChanged { text, start, before, count ->
        }

        btnSendOffer.setOnClickListener {
            viewModel.offerARide(passengerPost.id,
                                 if (edtPrice.text.isNullOrBlank()) passengerPost.price else edtPrice.text.toString()
                                     .toInt(),
                                 messageInput.text.toString(),
                                 passengerPost)
        }
        ivClearSelected.setOnClickListener {
            viewModel.clearOfferingPost()
            lblSelectPost.visibility = View.VISIBLE
            tvSelectedPost.visibility = View.GONE
            ivClearSelected.visibility = View.GONE
        }
    }

    @ExperimentalSplittiesApi
    private fun loadData(orders: List<DriverPost>) {
        adapter.clear()
        if (orders.isEmpty()) {
            lblSelectPost.text = getString(R.string.we_will_create_an_order_for_you)
            rvContainer.visibility = View.GONE
        } else {
            lblSelectPost.text = getString(R.string.select_your_trip_if_you_have_one)
            rvContainer.visibility = View.VISIBLE
        }



        orders.forEach { post ->
            adapter.add(ItemActivePost(post) {
                lblSelectPost.visibility = View.GONE
                tvSelectedPost.visibility = View.VISIBLE
                ivClearSelected.visibility = View.VISIBLE
                tvSelectedPost.text = getString(R.string.offering_post_id, post.id)
                viewModel.setOfferingPost(post.id)

            })
        }

    }

}