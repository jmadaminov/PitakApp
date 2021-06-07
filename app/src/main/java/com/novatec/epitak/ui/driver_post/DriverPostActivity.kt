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
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.novatec.core.*
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.epitak.R
import com.novatec.epitak.ui.BaseActivity
import com.novatec.epitak.ui.addpost.AddPostActivity
import com.novatec.epitak.ui.interfaces.IOnOfferActionListener
import com.novatec.epitak.ui.interfaces.IOnPassengerDelete
import com.novatec.epitak.ui.viewgroups.PassengerItem
import com.novatec.epitak.util.PostUtils
import com.novatec.epitak.viewobjects.DriverPostViewObj
import com.novatec.epitak.viewobjects.OfferViewObj.Companion.offerToViewObj
import com.novatec.remote.model.OfferDTO
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_driver_post.*
import splitties.experimental.ExperimentalSplittiesApi
import java.text.DecimalFormat
import java.text.SimpleDateFormat

const val EXTRA_POST_ID = "POST_ID"

@ExperimentalSplittiesApi
class DriverPostActivity : BaseActivity(), IOnPassengerDelete {

    companion object {
        const val REQ_POST_MANIPULATED: Int = 89
    }

    private lateinit var post: DriverPost
    var postId: Long = 0
    private val viewModel: DriverPostViewModel by viewModels()

    lateinit var offersAdapter: PostOffersAdapter
    val passengersAdapter = GroupAdapter<GroupieViewHolder>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_post)
        postId = intent.getLongExtra(EXTRA_POST_ID, 0)
        setupActionBar()
        offersAdapter = PostOffersAdapter(object : IOnOfferActionListener {
            override fun onCancelClick(offer: OfferDTO) {
                val dialog = DialogCancelOffer()
                dialog.arguments =
                    Bundle().apply { putParcelable(ARG_OFFER, offerToViewObj(offer)) }
                dialog.show(supportFragmentManager, "")
            }

            override fun onAcceptClick(offer: OfferDTO) {
                val dialog = DialogAcceptOffer()
                dialog.arguments =
                    Bundle().apply { putParcelable(ARG_OFFER, offerToViewObj(offer)) }
                dialog.show(supportFragmentManager, "")
            }

            override fun onPhoneCallClick(offer: OfferDTO) {
            }

        })

        offersAdapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    lblMyOffers.text =
                        if (offersAdapter.itemCount < 1) getString(R.string.you_have_no_offers_yet_come_back_later)
                        else getString(R.string.offers)
                }
                LoadState.Loading -> {

                }
                is LoadState.Error -> {

                }
            }
        }

        rvOffers.setHasFixedSize(true)
        rvOffers.adapter = offersAdapter
        rvPassengers.adapter = passengersAdapter
        viewModel.getPostById(postId)
        viewModel.getOffersForPost(postId)
        attachListeners()
        subscribes()
    }

    private fun subscribes() {

        viewModel.postOffers.observe(this) {
            val value = it
            offersAdapter.submitData(lifecycle, value)
            rvOffers.requestLayout()
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

    private fun showPostData() {

        if (post.postType == EPostType.DRIVER_PARCEL) {
            lblSeatsCount.isVisible = false
            llSeatsContainer.isVisible = false
            lblPricePerPassenger.text = getString(R.string.price)
        } else {
            lblSeatsCount.isVisible = true
            llSeatsContainer.isVisible = true
            lblPricePerPassenger.text = getString(R.string.willing_price_for_one)
        }

        edit.isVisible =
            post.postStatus == EPostStatus.CREATED && post.passengerList.isNullOrEmpty()
        done.isVisible = post.postStatus == EPostStatus.START
        cancel.isVisible =
            post.postStatus == EPostStatus.CREATED || post.postStatus == EPostStatus.WAITING_FOR_START

        btnStart.isVisible =
            post.postStatus == EPostStatus.WAITING_FOR_START || !post.passengerList.isNullOrEmpty() && post.postStatus == EPostStatus.CREATED


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
        var availableSeats = post.seat - post.passengerList!!.size
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

        if (post.passengerList != null && post.passengerList!!.isNotEmpty()) {
            lblMyPassengers.text = getString(R.string.your_passengers)
        } else {
            lblMyPassengers.text = getString(R.string.no_passengers_yet)
        }

        post.passengerList?.forEach {
            price.paintFlags = price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            passengersAdapter.add(PassengerItem(it) { passenger ->
                val dialog = DialogDeletePassenger().apply {
                    val args = Bundle()
                    args.putLong(ARG_PASSENGER_ID, passenger.profile!!.id.toLong())
                    arguments = args
                }
                dialog.show(supportFragmentManager, "")
            })
        }
    }

    private fun attachListeners() {

        btnStart.setOnClickListener {
            viewModel.startTrip(post.id!!)
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
        offersAdapter.refresh()
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
    fun acceptOffer(offer: OfferDTO) = viewModel.acceptOffer(offer.id)
    fun cancelOffer(offer: OfferDTO) = viewModel.cancelOffer(offer.id)

    override fun onPassengerDelete(passengerId: Long) {
        viewModel.deletePassenger(passengerId, postId)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQ_POST_MANIPULATED) {
            refreshAll()
        }
    }
}