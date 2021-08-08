package com.novatec.epitak.ui.driver_post

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.novatec.core.*
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.domain.domainmodel.Offer
import com.novatec.epitak.R
import com.novatec.epitak.ui.BaseActivity
import com.novatec.epitak.ui.addpost.AddPostActivity
import com.novatec.epitak.ui.interfaces.IOnParcelDelete
import com.novatec.epitak.ui.interfaces.IOnPassengerDelete
import com.novatec.epitak.ui.main.dialogs.ARG_IMG
import com.novatec.epitak.ui.main.dialogs.ImagePreviewDialog
import com.novatec.epitak.ui.viewgroups.*
import com.novatec.epitak.util.PostUtils
import com.novatec.epitak.viewobjects.DriverPostViewObj
import com.novatec.epitak.viewobjects.OfferViewObj.Companion.offerToViewObj
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_driver_post.*
import kotlinx.android.synthetic.main.item_add_car.*
import kotlinx.android.synthetic.main.view_directions.*
import splitties.experimental.ExperimentalSplittiesApi
import java.text.DecimalFormat
import java.text.SimpleDateFormat

const val EXTRA_POST_ID = "POST_ID"

@ExperimentalSplittiesApi
class DriverPostActivity : BaseActivity(), IOnPassengerDelete, IOnParcelDelete {

    companion object {
        const val REQ_POST_MANIPULATED: Int = 89
    }

    private lateinit var post: DriverPost
    var postId: Long = 0
    private val viewModel: DriverPostViewModel by viewModels()

    val passengersAdapter = GroupAdapter<GroupieViewHolder>()
    val psngrOfferAdr = GroupAdapter<GroupieViewHolder>()
    val parcelOfferAdr = GroupAdapter<GroupieViewHolder>()
    val parcelsAdr = GroupAdapter<GroupieViewHolder>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_post)
        postId = intent.getLongExtra(EXTRA_POST_ID, 0)
        setupActionBar()

        rvPassengers.adapter = passengersAdapter
        rvParcels.adapter = parcelsAdr
        rvParcelOffers.adapter = parcelOfferAdr
        rvOffers.adapter = psngrOfferAdr
        viewModel.getPostById(postId)

        attachListeners()
        subscribes()
    }

    private fun subscribes() {

        viewModel.passengerOffers.observe(this) {
            when (it) {
                is ErrorWrapper.RespError -> {
                    progressOfferAction.isVisible = false
                }
                is ErrorWrapper.SystemError -> {
                    progressOfferAction.isVisible = false
                }
                ResultWrapper.InProgress -> {
                    progressOfferAction.isVisible = true
                }
                is ResultWrapper.Success -> {
                    progressOfferAction.isVisible = false
                    populatePassengerOffers(it.value)
                }
            }
            rvOffers.requestLayout()
        }

        viewModel.parcelOffers.observe(this) {
            when (it) {
                is ErrorWrapper.RespError -> {
                    progressParcelOfferAction.isVisible = false
                }
                is ErrorWrapper.SystemError -> {
                    progressParcelOfferAction.isVisible = false
                }
                ResultWrapper.InProgress -> {
                    progressParcelOfferAction.isVisible = true
                }
                is ResultWrapper.Success -> {
                    progressParcelOfferAction.isVisible = false
                    populateParcelOffers(it.value)
                }
            }
            rvParcelOffers.requestLayout()
        }

        viewModel.postData.observe(this) {
            post = it ?: return@observe
            showPostData()
        }

        viewModel.offerActionLoading.observe(this) {
            progressOfferAction.visibility = if (it ?: return@observe) View.VISIBLE else View.GONE
        }

        viewModel.offerActionResp.observe(this) {
            refreshAll()
        }

        viewModel.isLoading.observe(this) {
            val value = it ?: return@observe
            swipeRefreshLayout.isRefreshing = value
        }

        viewModel.errorMessage.observe(this) {
            if (it.isNullOrBlank()) {
                tvMessage.visibility = View.GONE
                llOffersContainer.visibility = View.VISIBLE
            } else {
                llOffersContainer.visibility = View.GONE
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = it
            }
        }

        viewModel.offerActionError.observe(this) {
            Snackbar.make(swipeRefreshLayout, it ?: return@observe, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.startedTripResp.observe(this) {
            refreshAll()
        }

        viewModel.deletePostReponse.observe(this) {
            val response = it ?: return@observe
            when (response) {
                is ErrorWrapper.RespError -> {
                    Snackbar.make(swipeRefreshLayout, response.message!!, Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(swipeRefreshLayout,
                                  response.err.localizedMessage.toString(),
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {
                    setResult(RESULT_OK)
                    finish()
                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive
        }

        viewModel.finishPostResponse.observe(this) {
            val response = it ?: return@observe
            when (response) {
                is ErrorWrapper.RespError -> {
                    Snackbar.make(swipeRefreshLayout,
                                  response.message!!,
                                  Snackbar.LENGTH_SHORT).show()

                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(swipeRefreshLayout,
                                  response.err.localizedMessage.toString(),
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {
                    setResult(RESULT_OK)
                    finish()
                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive
        }


    }

    private fun populatePassengerOffers(value: List<Offer>) {
        psngrOfferAdr.clear()

        if (value.isEmpty()) {
//            rvOffers.isVisible = false
            psngrOfferAdr.add(ErrorTextItem(getString(R.string.you_have_no_offers_yet_come_back_later)))
        } else {
//            rvOffers.isVisible = true
            value.forEach {
                psngrOfferAdr.add(ItemPassengerOffer(it, { offer ->
                    val dialog = DialogAcceptOffer()
                    dialog.arguments =
                        Bundle().apply { putParcelable(ARG_OFFER, offerToViewObj(offer)) }
                    dialog.show(supportFragmentManager, "")
                }) { offer ->
                    val dialog = DialogCancelOffer()
                    dialog.arguments =
                        Bundle().apply { putParcelable(ARG_OFFER, offerToViewObj(offer)) }
                    dialog.show(supportFragmentManager, "")
                })
            }
        }
    }

    private fun populateParcelOffers(value: List<Offer>) {
        parcelOfferAdr.clear()
        if (value.isEmpty()) {
//            rvOffers.isVisible = false
            parcelOfferAdr.add(ErrorTextItem(getString(R.string.you_have_no_parcel_offers_yet_come_back_later)))
        } else {
            value.forEach {
                parcelOfferAdr.add(ItemParcelOffer(it, { offer ->
                    val dialog = DialogAcceptOffer()
                    dialog.arguments =
                        Bundle().apply { putParcelable(ARG_OFFER, offerToViewObj(offer)) }
                    dialog.show(supportFragmentManager, "")
                }, { offer ->

                                                       val dialog = DialogCancelOffer()
                                                       dialog.arguments =
                                                           Bundle().apply {
                                                               putParcelable(ARG_OFFER,
                                                                             offerToViewObj(
                                                                                 offer))
                                                           }
                                                       dialog.show(supportFragmentManager, "")
                                                   }) { imageUrl ->
                    ImagePreviewDialog().apply {
                        arguments = Bundle().apply { putString(ARG_IMG, imageUrl) }
                    }.show(supportFragmentManager, "")
                })
            }
        }

    }

    private fun showPostData() {

        if (post.postType == EPostType.DRIVER_PARCEL) {
            lblPassengersCount.isVisible = false
            llParcel.isVisible = true
            lblPricePerPassenger.text = getString(R.string.price)
            viewModel.getParcelOffersByPostId(postId)
            rvOffers.isVisible = false
            lblMyOffers.isVisible = false
            progressOfferAction.isVisible = false
        } else {
            viewModel.getPassengerOffersByPostId(postId)
            lblMyOffers.isVisible = true
            if (post.pkg == true) {
                llParcel.isVisible = true
                rvParcelOffers.isVisible = true
                lblParcelOffers.isVisible = true
                progressParcelOfferAction.isVisible = true
                viewModel.getParcelOffersByPostId(postId)
            }
            lblPassengersCount.isVisible = true
            lblPricePerPassenger.text = getString(R.string.willing_price_for_one)
        }

        when (post.postStatus) {
            EPostStatus.WAITING_FOR_START -> {
                rvOffers.isVisible = false
                lblMyOffers.isVisible = false
            }
            EPostStatus.START -> {
                rvOffers.isVisible = false
                rvParcelOffers.isVisible = false
                lblMyOffers.isVisible = false
                lblParcelOffers.isVisible = false
                lblMyParcels.isVisible = false
            }
            else -> {
                rvOffers.isVisible = true
                rvParcelOffers.isVisible = true
                lblMyOffers.isVisible = true
                lblParcelOffers.isVisible = true
                lblMyParcels.isVisible = true
            }
        }


        edit.isVisible =
            post.postStatus == EPostStatus.CREATED && post.passengerList.isNullOrEmpty()
        done.isVisible = post.postStatus == EPostStatus.START
        cancel.isVisible =
            post.postStatus == EPostStatus.CREATED || post.postStatus == EPostStatus.WAITING_FOR_START

        btnStart.isVisible =
            post.postStatus == EPostStatus.WAITING_FOR_START || !post.passengerList.isNullOrEmpty() || !post.parcelList.isNullOrEmpty() && post.postStatus == EPostStatus.CREATED


        cbTakeParcel.isVisible = post.pkg ?: false

        if (post.from.name == null && post.from.districtName == null) {
            fromDistrict.isVisible = false
            from.text = post.from.regionName
        } else {
            fromDistrict.isVisible = true
            fromDistrict.text = post.from.regionName ?: post.from.name
            from.text = post.from.districtName
        }

        if (post.to.name == null && post.to.districtName == null) {
            toDistrict.isVisible = false
            to.text = post.to.regionName
        } else {
            toDistrict.isVisible = true
            toDistrict.text = post.to.regionName ?: post.to.name
            to.text = post.to.districtName
        }

        llSeatsContainer.removeAllViews()
        var availableSeats = post.availableSeats!!
        for (i in 0 until post.seat) {
            val seat = ImageView(this)
            seat.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                          ViewGroup.LayoutParams.WRAP_CONTENT)
            seat.setImageResource(R.drawable.ic_round_event_seat_24)
            if (availableSeats > 0) {
                seat.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary))
                availableSeats--
            } else {
                seat.setColorFilter(ContextCompat.getColor(this, R.color.green))
            }
            llSeatsContainer.addView(seat)
        }


        time.text = PostUtils.timeFromDayParts(post.timeFirstPart,
                                               post.timeSecondPart,
                                               post.timeThirdPart,
                                               post.timeFourthPart)

        date.text = DateFormat.format("dd MMMM",
                                      SimpleDateFormat("dd.MM.yyyy").parse(post.departureDate))
            .toString()


        price.text = DecimalFormat("#,###").format(post.price) + " " + getString(R.string.sum)

        if (post.remark.isNullOrBlank()) {
            note.visibility = View.GONE
        } else {
            note.visibility = View.VISIBLE
            note.text = post.remark
        }

        passengersAdapter.clear()
        parcelsAdr.clear()

        if (post.passengerList != null && post.passengerList!!.isNotEmpty()) {
            lblMyPassengers.text = getString(R.string.your_passengers)
        } else {
            lblMyPassengers.text = getString(R.string.no_passengers_yet)
            lblMyPassengers.isVisible = false
        }

        if (post.parcelList != null && post.parcelList!!.isNotEmpty()) {
            lblMyParcels.text = getString(R.string.your_parcels)
        } else {
            lblMyParcels.text = getString(R.string.no_parcels_yet)
            lblMyParcels.isVisible = false
        }

        post.passengerList?.forEach {
            price.paintFlags = price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            passengersAdapter.add(ItemPassenger(it) { passenger ->
                val dialog = DialogDeletePassenger().apply {
                    val args = Bundle()
                    args.putLong(ARG_PASSENGER_ID, passenger.id!!)
                    arguments = args
                }
                dialog.show(supportFragmentManager, "")
            })
        }
        post.parcelList?.forEach {
            parcelsAdr.add(ItemParcel(it) { parcel ->
                val dialog = DialogDeleteParcel().apply {
                    val args = Bundle()
                    args.putLong(ARG_PARCEL_ID, parcel.id)
                    arguments = args
                }
                dialog.show(supportFragmentManager, "")
            })
        }
    }

    private fun attachListeners() {

        btnStart.setOnClickListener {
            viewModel.startTrip(post.id)
        }

        swipeRefreshLayout.setOnRefreshListener {
            refreshAll()
        }

        done.setOnClickListener {
            DialogFinishPost().show(supportFragmentManager, "")
        }

        cancel.setOnClickListener {
            DialogDeletePost().show(supportFragmentManager, "")
        }

        edit.setOnClickListener {
            startActivityForResult(Intent(this, AddPostActivity::class.java).apply {
                putExtra(Constants.TXT_DRIVER_POST, DriverPostViewObj.fromDriverPost(post))
            }, REQ_POST_MANIPULATED)
        }
    }

    private fun refreshAll() {
        viewModel.getPostById(postId)
        viewModel.getPassengerOffersByPostId(postId)
        viewModel.getParcelOffersByPostId(postId)
//        offersAdapter.refresh()
    }


    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = getString(R.string.post, postId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun finishPost() = viewModel.finishPost(post.id.toString())
    fun deletePost() = viewModel.deletePost(post.id.toString())
    fun acceptOffer(offerId: Long) = viewModel.acceptOffer(offerId)
    fun cancelOffer(offerId: Long) = viewModel.cancelOffer(offerId)

    override fun onPassengerDelete(commuterId: Long) {
        viewModel.deletePassenger(commuterId, postId)
    }

    override fun onParcelDelete(parcelId: Long) {
        viewModel.deleteParcel(parcelId, postId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQ_POST_MANIPULATED) {
            refreshAll()
        }
    }

}
