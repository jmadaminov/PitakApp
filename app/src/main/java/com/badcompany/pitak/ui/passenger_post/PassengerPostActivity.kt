package com.badcompany.pitak.ui.passenger_post

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.BaseActivity
import com.badcompany.pitak.ui.dialogs.DialogAddCarFirst
import com.badcompany.pitak.ui.passenger_post.offer_a_ride.ARG_PASSENGER_POST
import com.badcompany.pitak.ui.passenger_post.offer_a_ride.DialogOfferARideFragment
import com.badcompany.pitak.util.AppPrefs
import com.badcompany.pitak.util.loadCircleImageUrl
import com.badcompany.pitak.viewobjects.PassengerPostViewObj
import kotlinx.android.synthetic.main.activity_passenger_post.*
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

            if (AppPrefs.defaultCarId.isNullOrBlank()) {
                DialogAddCarFirst().show(supportFragmentManager, "")
            } else {
                val dialog = DialogOfferARideFragment()
                dialog.arguments =
                    Bundle().apply { putParcelable(ARG_PASSENGER_POST, passengerPost) }
                dialog.show(supportFragmentManager, "")
            }


        }

        swipeRefreshLayout.setOnRefreshListener {

        }

    }


    private fun showPostData(post: PassengerPostViewObj) {
        date.text = post.departureDate
        seats.text = post.seat.toString()
        from.text = post.from.regionName
        to.text = post.to.regionName
        price.text =
            DecimalFormat("#,###").format(post.price) + " " + getString(R.string.sum)

        post.remark?.also {
            note.visibility = View.VISIBLE
            note.text = post.remark
        } ?: run {
            note.visibility = View.GONE
        }


        post.profileViewObj.let {
            tvPassengerName.text = it.name + " " + it.surname
        }

        post.profileViewObj.image?.link?.let {
            ivPassenger.loadCircleImageUrl(it)
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