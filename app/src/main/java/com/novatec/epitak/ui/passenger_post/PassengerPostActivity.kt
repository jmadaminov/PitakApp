package com.novatec.epitak.ui.passenger_post

import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.novatec.core.EPostType
import com.novatec.epitak.R
import com.novatec.epitak.ui.BaseActivity
import com.novatec.epitak.ui.dialogs.DialogAddCarFirst
import com.novatec.epitak.ui.passenger_post.offer_a_ride.ARG_PASSENGER_POST
import com.novatec.epitak.ui.passenger_post.offer_a_ride.DialogOfferARideFragment
import com.novatec.epitak.util.AppPrefs
import com.novatec.epitak.util.PostUtils
import com.novatec.epitak.util.loadRound
import com.novatec.epitak.viewobjects.PassengerPostViewObj
import kotlinx.android.synthetic.main.activity_passenger_post.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class PassengerPostActivity : BaseActivity() {

    val viewModel: PassengerPostViewModel by viewModels()

    private lateinit var passengerPost: PassengerPostViewObj


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_post)
        passengerPost = intent.getParcelableExtra(ARG_PASSENGER_POST)!!
        setupActionBar()

        attachListeners()
        subscribeObservers()
//        showPostData(passengerPost)
    }

    private fun subscribeObservers() {

        viewModel.isLoading.observe(this) {
            swipeRefreshLayout.isRefreshing = it ?: return@observe

        }

        viewModel.postData.observe(this) {
            val result = it ?: return@observe
            passengerPost = PassengerPostViewObj.mapFromPassengerPostModel(result)
            showPostData(passengerPost)
        }

    }

    private fun attachListeners() {

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPostById(passengerPost.id)
        }

        btnOfferARide.setOnClickListener {

            if (AppPrefs.defaultCarId.isNullOrBlank()) {
                DialogAddCarFirst().show(supportFragmentManager, "")
            } else {
                val dialog = DialogOfferARideFragment()
                dialog.arguments =
                    Bundle().apply { putParcelable(ARG_PASSENGER_POST, passengerPost) }
                dialog.show(supportFragmentManager, "")
            }


        }


    }


    private fun showPostData(post: PassengerPostViewObj) {

        if (post.postType == EPostType.PARCEL_SM) {
            lblPricePerPassenger.text = getString(R.string.price)
            llSeatsContainer.isVisible = false
            cbTakeParcel.isVisible = true
            imageContainer.isVisible = true
            lblPassengersCount.isVisible = false
//            parcelImage.loadRounded(post.imageList)
        } else {
            lblPassengersCount.isVisible = true
            lblPricePerPassenger.text = getString(R.string.price_for_one)
            llSeatsContainer.isVisible = true
            cbTakeParcel.isVisible = false
            imageContainer.isVisible = false
            llSeatsContainer.removeAllViews()
            for (i in 0 until post.seat) {
                val seat = ImageView(this)
                seat.layoutParams =
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                              ViewGroup.LayoutParams.WRAP_CONTENT)
                seat.setImageResource(R.drawable.ic_round_emoji_people_24)
                llSeatsContainer.addView(seat)
            }

        }

        time.text = PostUtils.timeFromDayParts(post.timeFirstPart,
                                               post.timeSecondPart,
                                               post.timeThirdPart,
                                               post.timeFourthPart)

        date.text = DateFormat.format("dd MMMM",
                                      SimpleDateFormat("dd.MM.yyyy").parse(post.departureDate))
            .toString()
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

        price.text =
            DecimalFormat("#,###").format(post.price) + " " + getString(R.string.sum)

        if (post.remark.isNullOrBlank()) {
            note.visibility = View.GONE
        } else {
            note.visibility = View.VISIBLE
            note.text = post.remark
        }

        post.profileViewObj.let {
            tvPassengerName.text = it.name + " " + it.surname
        }

        post.profileViewObj.image?.link?.let {
            ivPassenger.loadRound(it)
        } ?: run {
            ivPassenger.setImageResource(R.drawable.ic_baseline_account_circle_24)
        }

    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = getString(R.string.post, passengerPost.id)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        super.onResume()
        refreshPost()
    }

    fun refreshPost() {
        viewModel.getPostById(passengerPost.id)
    }
}