package com.novatec.pitak.ui.passenger_post

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.novatec.pitak.R
import com.novatec.pitak.ui.BaseActivity
import com.novatec.pitak.ui.dialogs.DialogAddCarFirst
import com.novatec.pitak.ui.passenger_post.offer_a_ride.ARG_PASSENGER_POST
import com.novatec.pitak.ui.passenger_post.offer_a_ride.DialogOfferARideFragment
import com.novatec.pitak.util.AppPrefs
import com.novatec.pitak.util.loadCircleImageUrl
import com.novatec.pitak.viewobjects.PassengerPostViewObj
import kotlinx.android.synthetic.main.activity_driver_post.*
import kotlinx.android.synthetic.main.activity_passenger_post.*
import kotlinx.android.synthetic.main.activity_passenger_post.date
import kotlinx.android.synthetic.main.activity_passenger_post.from
import kotlinx.android.synthetic.main.activity_passenger_post.fromDistrict
import kotlinx.android.synthetic.main.activity_passenger_post.note
import kotlinx.android.synthetic.main.activity_passenger_post.price
import kotlinx.android.synthetic.main.activity_passenger_post.seats
import kotlinx.android.synthetic.main.activity_passenger_post.swipeRefreshLayout
import kotlinx.android.synthetic.main.activity_passenger_post.to
import kotlinx.android.synthetic.main.activity_passenger_post.toDistrict
import kotlinx.android.synthetic.main.item_offer.view.*
import java.text.DecimalFormat

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

        viewModel.isLoading.observe(this, {
            swipeRefreshLayout.isRefreshing = it ?: return@observe
        })

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
        date.text = post.departureDate
        seats.text = post.seat.toString()
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
            ivPassenger.loadCircleImageUrl(it)
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