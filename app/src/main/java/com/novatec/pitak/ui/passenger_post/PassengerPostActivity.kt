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

        viewModel.isLoading.observe(this, {
            swipeRefreshLayout.isRefreshing = it ?: return@observe
        })

        viewModel.postData.observe(this) {
            val result = it ?: return@observe
            showPostData(PassengerPostViewObj.mapFromPassengerPostModel(result))
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
        val fromLbl = StringBuilder()
        val toLbl = StringBuilder()

        post.from.districtName?.let {
            fromLbl.append(" $it")
        }
        if (fromLbl.isBlank()) post.from.name?.let { fromLbl.append(it) }
        post.from.regionName?.let {
            fromDistrict.isVisible = true
            fromDistrict.text = it
        }

        post.to.districtName?.let { toLbl.append(" $it") }
        if (toLbl.isBlank()) post.to.name?.let { toLbl.append(it) }
        post.to.regionName?.let {
            toDistrict.isVisible = true
            toDistrict.text = it
        }

        from.text = fromLbl
        to.text = toLbl
        price.text =
            DecimalFormat("#,###").format(post.price) + " " + getString(R.string.sum)

        if (post.remark.isNullOrBlank()){
            note.visibility = View.GONE
        }else{
            note.visibility = View.VISIBLE
            note.text = post.remark
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