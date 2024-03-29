package com.novatec.epitak.ui.main.searchtrip

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.novatec.core.EPostType
import com.novatec.epitak.R
import com.novatec.epitak.ui.passenger_post.PassengerPostActivity
import com.novatec.epitak.ui.passenger_post.offer_a_ride.ARG_PASSENGER_POST
import com.novatec.epitak.util.PostUtils.timeFromDayParts
import com.novatec.epitak.util.SizeUtils
import com.novatec.epitak.util.loadRound
import com.novatec.epitak.viewobjects.PassengerPostViewObj
import com.novatec.remote.model.PassengerPostModel
import kotlinx.android.synthetic.main.item_passenger_post.view.*
import splitties.activities.start
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class PostFilterAdapter :
    PagingDataAdapter<PassengerPostModel, PostFilterAdapter.PassengerPostViewHolder>(
        FILTER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerPostViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_passenger_post, parent, false)
        return PassengerPostViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: PassengerPostViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem)
    }

    class PassengerPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: PassengerPostModel) {
            itemView.apply {

                if (post.postType == EPostType.PASSENGER_PARCEL) {
                    ivParcel.isVisible = true
                    llSeatsContainer.isVisible = false
                } else {
                    ivParcel.isVisible = false
                    llSeatsContainer.isVisible = true

                    llSeatsContainer.removeAllViews()
                    for (i in 0 until post.seat) {
                        val seat = ImageView(context)
                        seat.layoutParams =
                            LinearLayout.LayoutParams(SizeUtils.dpToPx(context, 25),
                                                      SizeUtils.dpToPx(context, 30))
                        seat.setImageResource(R.drawable.ic_round_emoji_people_24)
                        seat.scaleType = ImageView.ScaleType.FIT_XY
                        llSeatsContainer.addView(seat)
                    }
                }

                time.text = timeFromDayParts(post.timeFirstPart,
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
                    DecimalFormat("#,###").format(post.price) + " " + itemView.context.getString(R.string.sum)

                card.setOnClickListener {
                    context.start<PassengerPostActivity> {
                        putExtra(ARG_PASSENGER_POST,
                                 PassengerPostViewObj.mapFromPassengerPostModel(post))
                    }
                }

                post.profile.let {
                    tvPassengerName.text = it.name + " " + it.surname
                }

                post.profile.image?.link?.let {
                    ivPassenger.loadRound(it)
                } ?: run {
                    ivPassenger.setImageResource(R.drawable.ic_baseline_account_circle_24)
                }

            }
        }
    }

    companion object {

        private val FILTER_COMPARATOR = object : DiffUtil.ItemCallback<PassengerPostModel>() {
            override fun areItemsTheSame(oldItem: PassengerPostModel, newItem: PassengerPostModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PassengerPostModel,
                                            newItem: PassengerPostModel) =
                oldItem == newItem

        }
    }

}