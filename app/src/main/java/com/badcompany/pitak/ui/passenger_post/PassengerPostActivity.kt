package com.badcompany.pitak.ui.passenger_post

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.BaseActivity
import com.badcompany.pitak.ui.passenger_post.offer_a_ride.ARG_PASSENGER_POST
import com.badcompany.pitak.ui.passenger_post.offer_a_ride.DialogOfferARideFragment
import com.badcompany.pitak.viewobjects.PassengerPostViewObj
import kotlinx.android.synthetic.main.activity_passenger_post.*

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
        showPostData(passengerPost)
    }

    private fun subscribeObservers() {

//        viewModel.isLoading.observe(this, {
//            val value = it ?: return@observe
//            swipeRefreshLayout.isRefreshing = value
//        })

    }

    private fun attachListeners() {
        btnOfferARide.setOnClickListener {
            val dialog = DialogOfferARideFragment()
            dialog.arguments = Bundle().apply { putParcelable(ARG_PASSENGER_POST, passengerPost) }
            dialog.show(supportFragmentManager, "")
        }

    }


    private fun showPostData(post: PassengerPostViewObj) {
        date.text = post.departureDate
        from.text = post.from.regionName
        to.text = post.to.regionName
        price.text =
            getString(R.string.price_and_seats_format,
                      post.price.toString(), post.seat.toString())
//        seats.text = post.seat.toString()

        if (!post.remark.isBlank()) {
            note.visibility = View.VISIBLE
            note.text = post.remark
        } else {
            note.visibility = View.GONE
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


}