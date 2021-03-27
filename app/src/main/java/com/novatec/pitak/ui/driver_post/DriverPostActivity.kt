package com.novatec.pitak.ui.driver_post

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.novatec.core.*
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.pitak.R
import com.novatec.pitak.ui.BaseActivity
import com.novatec.pitak.ui.addpost.AddPostActivity
import com.novatec.pitak.ui.interfaces.IOnOfferActionListener
import com.novatec.pitak.ui.interfaces.IOnPassengerDelete
import com.novatec.pitak.ui.viewgroups.PassengerItem
import com.novatec.pitak.viewobjects.DriverPostViewObj
import com.novatec.pitak.viewobjects.OfferViewObj.Companion.offerToViewObj
import com.novatec.remote.model.OfferDTO
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_driver_post.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi
import java.text.DecimalFormat

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

        rvOffers.setHasFixedSize(true)
        rvOffers.adapter = offersAdapter
        rvPassengers.adapter = passengersAdapter
        viewModel.getPostById(postId)
        viewModel.getOffersForPost(postId)

        attachListeners()
        subscribes()
    }

    private fun subscribes() {

        viewModel.postOffers.observe(this, {
            val value = it ?: return@observe
            offersAdapter.submitData(lifecycle, value)
            rvOffers.requestLayout()
        })

        viewModel.postData.observe(this, {
            post = it ?: return@observe
            showPostData()
        })

        viewModel.offerActionLoading.observe(this, {
            progressOfferAction.visibility = if (it ?: return@observe) View.VISIBLE else View.GONE
        })

        viewModel.offerActionResp.observe(this, {
            viewModel.getPostById(postId)
            viewModel.getOffersForPost(postId)
            offersAdapter.refresh()
        })

        viewModel.isLoading.observe(this, {
            val value = it ?: return@observe
            swipeRefreshLayout.isRefreshing = value
        })

        viewModel.errorMessage.observe(this, {
            if (it.isNullOrBlank()) {
                tvMessage.visibility = View.GONE
                llOffersContainer.visibility = View.VISIBLE
            } else {
                llOffersContainer.visibility = View.GONE
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = it
            }
        })

        viewModel.offerActionError.observe(this, {
            Snackbar.make(swipeRefreshLayout, it ?: return@observe, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.startedTripResp.observe(this, {
            viewModel.getOffersForPost(postId)
            cancel.visibility = View.GONE
            offersAdapter.refresh()
        })



        viewModel.deletePostReponse.observe(this, {
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
        })

        viewModel.finishPostResponse.observe(this, {
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
        })


    }

    private fun showPostData() {
        edit.isVisible =
            post.postStatus == EPostStatus.CREATED && post.passengerList.isNullOrEmpty()
        done.isVisible = post.postStatus == EPostStatus.START
        cancel.isVisible =
            post.postStatus == EPostStatus.CREATED || post.postStatus == EPostStatus.WAITING_FOR_START

        btnStart.isVisible =
            post.postStatus == EPostStatus.WAITING_FOR_START || !post.passengerList.isNullOrEmpty() && post.postStatus == EPostStatus.CREATED

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


        seats.text = "${post.passengerCount}/${post.seat}"
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
            passengersAdapter.add(PassengerItem(it) { passenger ->
                val dialog = DialogDeletePassenger().apply {
                    val args = Bundle()
                    args.putLong(ARG_PASSENGER_ID, passenger.id!!)
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
            viewModel.getPostById(postId)
            viewModel.getOffersForPost(postId)
            offersAdapter.refresh()
        }

        done.setOnClickListener {
            DialogFinishPost().show(supportFragmentManager, "")
        }

        cancel.setOnClickListener {
            DialogDeletePost().show(supportFragmentManager, "")
        }

        edit.setOnClickListener {
            start<AddPostActivity> {
                putExtra(Constants.TXT_DRIVER_POST, DriverPostViewObj.fromDriverPost(post))
            }
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

    fun finishPost() = viewModel.finishPost(post.id.toString())
    fun deletePost() = viewModel.deletePost(post.id.toString())
    fun acceptOffer(offer: OfferDTO) = viewModel.acceptOffer(offer.id)
    fun cancelOffer(offer: OfferDTO) = viewModel.cancelOffer(offer.id)

    override fun onPassengerDelete(passengerId: Long) {
        viewModel.deletePassenger(passengerId, postId)

    }


}
