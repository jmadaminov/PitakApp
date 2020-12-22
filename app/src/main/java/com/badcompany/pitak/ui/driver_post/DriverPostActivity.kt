package com.badcompany.pitak.ui.driver_post

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.badcompany.core.Constants
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.BaseActivity
import com.badcompany.pitak.ui.addpost.AddPostActivity
import com.badcompany.pitak.ui.interfaces.IOnOfferActionListener
import com.badcompany.pitak.viewobjects.DriverPostViewObj
import com.badcompany.pitak.viewobjects.OfferViewObj.Companion.offerToViewObj
import com.badcompany.pitak.viewobjects.PlaceViewObj
import com.badcompany.remote.model.OfferDTO
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_driver_post.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi

const val EXTRA_POST_ID = "POST_ID"

@ExperimentalSplittiesApi class DriverPostActivity : BaseActivity() {

    companion object {
        const val REQ_POST_MANIPULATED: Int = 89
    }

    private lateinit var post: DriverPost
    var postId: Long = 0
    private val viewModel: DriverPostViewModel by viewModels()

    lateinit var offersAdapter: PostOffersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_post)
        postId = intent.getLongExtra(EXTRA_POST_ID, 0)
        setupActionBar()
        offersAdapter = PostOffersAdapter(object : IOnOfferActionListener {
            override fun onCancelClick(offer: OfferDTO) {
                val dialog = DialogCancelOffer()
                dialog.arguments = Bundle().apply { putParcelable(ARG_OFFER, offerToViewObj(offer)) }
                dialog.show(supportFragmentManager, "")
            }

            override fun onAcceptClick(offer: OfferDTO) {
                val dialog = DialogAcceptOffer()
                dialog.arguments = Bundle().apply { putParcelable(ARG_OFFER, offerToViewObj(offer) ) }
                dialog.show(supportFragmentManager, "")
            }

            override fun onPhoneCallClick(offer: OfferDTO) {
            }

        })

        rvOffers.setHasFixedSize(true)
        rvOffers.adapter = offersAdapter

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
            viewModel.getOffersForPost(postId)
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

        viewModel.deletePostReponse.observe(this, {
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
        date.text = post.departureDate
        from.text = post.from.regionName
        to.text = post.to.regionName
        price.text =
            getString(R.string.price_and_seats_format,
                      post.price.toString(), post.seat.toString())

        if (!post.remark.isBlank()) {
            note.visibility = View.VISIBLE
            note.text = post.remark
        } else {
            note.visibility = View.GONE
        }
    }

    private fun attachListeners() {

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPostById(postId)
            viewModel.getOffersForPost(postId)
        }

        done.setOnClickListener {
            DialogFinishPost().show(supportFragmentManager, "")
        }

        cancel.setOnClickListener {
            DialogDeletePost().show(supportFragmentManager, "")
//            viewModel.deletePost(post.id.toString())
        }

        edit.setOnClickListener {
            val from = PlaceViewObj(post.from.districtId,
                                    post.from.regionId,
                                    post.from.lat,
                                    post.from.lon,
                                    post.from.regionName,
                                    post.from.name)

            val to = PlaceViewObj(post.to.districtId,
                                  post.to.regionId,
                                  post.to.lat,
                                  post.to.lon,
                                  post.to.regionName,
                                  post.to.name)

            start<AddPostActivity> {

                putExtra(Constants.TXT_DRIVER_POST,
                         DriverPostViewObj(from,
                                              to,
                                              post.price,
                                              post.departureDate,
                                              post.timeFirstPart,
                                              post.timeSecondPart,
                                              post.timeThirdPart,
                                              post.timeFourthPart,
                                              null,
                                              null,
                                              post.remark,
                                              post.seat,
                                              post.postType))
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


}
