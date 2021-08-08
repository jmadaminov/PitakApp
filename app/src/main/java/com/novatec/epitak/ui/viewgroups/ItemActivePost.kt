package com.novatec.epitak.ui.viewgroups

import android.text.format.DateFormat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.novatec.core.EPostStatus
import com.novatec.domain.domainmodel.DriverPost
import com.novatec.epitak.R
import com.novatec.epitak.util.PostUtils
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_active_post.view.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import kotlin.to


class ItemActivePost(var post: DriverPost, val onClick: () -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            llSeatsContainer.removeAllViews()
            var availableSeats = post.seat - post.passengerList!!.size
            for (i in 0 until post.seat) {
                val seat = ImageView(context)
                seat.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                              ViewGroup.LayoutParams.WRAP_CONTENT)
                seat.setImageResource(R.drawable.ic_round_event_seat_24)
                if (availableSeats > 0) {
                    seat.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary))
                    availableSeats--
                } else {
                    seat.setColorFilter(ContextCompat.getColor(context, R.color.green))
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
                DecimalFormat("#,###").format(post.price) + " " + context.getString(R.string.sum)

            if (post.passengerOfferCount != null && post.passengerOfferCount!! > 0) {
                tvOffersCount.visibility = View.VISIBLE
                tvOffersCount.text = post.passengerOfferCount.toString()
            } else {
                tvOffersCount.visibility = View.GONE
            }

            val currentStatusStr = when (post.postStatus!!) {
                EPostStatus.WAITING_FOR_START -> {
                    llStatus.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.colorNavIdle)

                    context.getString(R.string.waiting)
                }
                EPostStatus.START -> {
                    llStatus.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.colorPrimary)

                    context.getString(R.string.on_the_way)
                }
                EPostStatus.CANCELED -> {
                    context.getString(R.string.canceled)
                }
                EPostStatus.FINISHED -> {
                    context.getString(R.string.completed)
                }
                EPostStatus.REJECTED -> {
                    context.getString(R.string.rejected)
                }
                EPostStatus.CREATED -> {
                    llStatus.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.neutralColor)
                    context.getString(R.string.boarding)
                }
                EPostStatus.SYSTEM_REJECTED -> {
                    context.getString(R.string.rejected)
                }
            }


            ivPkg.isVisible = post.pkg ?: false
            tvPkgOffersCount.isVisible = post.parcelOfferCount ?: 0 > 0
            tvPkgOffersCount.text = post.parcelOfferCount.toString()

            status.text = currentStatusStr

            if (findViewById<View>(R.id.progress) != null) {
                cl_parent.removeView(findViewById(R.id.progress))
            }

            cardParent.setOnClickListener {
                onClick()
            }
        }
    }

    override fun getLayout() = R.layout.item_active_post
}
