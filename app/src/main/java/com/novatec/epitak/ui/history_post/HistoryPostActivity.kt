package com.novatec.epitak.ui.history_post

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.epitak.R
import com.novatec.epitak.ui.BaseActivity
import com.novatec.epitak.ui.driver_post.EXTRA_POST_ID
import com.novatec.epitak.ui.viewgroups.PassengerItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_driver_post.*
import kotlinx.android.synthetic.main.activity_history_post.*
import kotlinx.android.synthetic.main.activity_history_post.date
import kotlinx.android.synthetic.main.activity_history_post.from
import kotlinx.android.synthetic.main.activity_history_post.fromDistrict
import kotlinx.android.synthetic.main.activity_history_post.lblMyPassengers
import kotlinx.android.synthetic.main.activity_history_post.llOffersContainer
import kotlinx.android.synthetic.main.activity_history_post.note
import kotlinx.android.synthetic.main.activity_history_post.price
import kotlinx.android.synthetic.main.activity_history_post.rvPassengers
import kotlinx.android.synthetic.main.activity_history_post.seats
import kotlinx.android.synthetic.main.activity_history_post.to
import kotlinx.android.synthetic.main.activity_history_post.toDistrict
import kotlinx.android.synthetic.main.activity_history_post.tvMessage
import java.text.DecimalFormat

class HistoryPostActivity : BaseActivity() {


    val viewModel: HistoryPostViewModel by viewModels()
    var postId: Long = 0
    private lateinit var post: DriverPost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_post)
        setupActionBar()
        postId = intent.getLongExtra(EXTRA_POST_ID, 0)

        rvPassengers.adapter = passengersAdapter


        viewModel.getPostById(postId)


        attachListeners()
        subscribes()
    }

    private fun attachListeners() {


    }

    private fun subscribes() {
        viewModel.postData.observe(this) {
            post = it ?: return@observe
            showPostData()
        }

        viewModel.isLoading.observe(this) {
            val value = it ?: return@observe
            progress.isVisible = value

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
    }


    val passengersAdapter = GroupAdapter<GroupieViewHolder>()

    private fun showPostData() {


        date.text = post.departureDate
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

        seats.text = "${post.passengerList!!.size!!}/${post.seat}"
        price.text = DecimalFormat("#,###").format(post.price) + " " + getString(R.string.sum)

        post.remark?.also {
            note.visibility = View.VISIBLE
            note.text = post.remark
        } ?: run { note.visibility = View.GONE }

        passengersAdapter.clear()

        if (post.passengerList != null && post.passengerList!!.isNotEmpty()) {
            lblMyPassengers.text = getString(R.string.your_passengers)
        } else {
            lblMyPassengers.text = getString(R.string.no_passengers_yet)
        }

        post.passengerList?.forEach {
            passengersAdapter.add(PassengerItem(it,  true) {

            })
        }
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

}
