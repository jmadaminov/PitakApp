package com.novatec.pitak.ui.main.mytrips.historytrips

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
import com.novatec.pitak.R
import com.novatec.pitak.ui.driver_post.EXTRA_POST_ID
import com.novatec.pitak.ui.history_post.HistoryPostActivity
import com.novatec.pitak.util.PostUtils.timeFromDayParts
import com.novatec.remote.model.DriverPostModel
import kotlinx.android.synthetic.main.item_history_post.view.*
import splitties.activities.start
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class HistoryPostAdapter :
    PagingDataAdapter<DriverPostModel, HistoryPostAdapter.DriverPostViewHolder>(
        FILTER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverPostViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_post, parent, false)
        return DriverPostViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: DriverPostViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem)
    }

    class DriverPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: DriverPostModel) {
            itemView.apply {
                llSeatsContainer.removeAllViews()
                for (i in 0 until post.seat) {
                    val seat = ImageView(context)
                    seat.layoutParams =
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                  ViewGroup.LayoutParams.WRAP_CONTENT)
                    seat.setImageResource(R.drawable.ic_round_emoji_people_24)
                    llSeatsContainer.addView(seat)
                }
                time.text = timeFromDayParts(post.timeFirstPart,
                                             post.timeSecondPart,
                                             post.timeThirdPart,
                                             post.timeFourthPart)

                date.text = DateFormat.format("dd MMMM yyyy",
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
                    DecimalFormat("#,###").format(post.price * post.seat) + " " + itemView.context.getString(
                        R.string.sum)
                cardHistoryItem.setOnClickListener {
                    context.start<HistoryPostActivity> {
                        putExtra(EXTRA_POST_ID, post.id)
                    }
                }



            }
        }
    }

    companion object {

        private val FILTER_COMPARATOR = object : DiffUtil.ItemCallback<DriverPostModel>() {
            override fun areItemsTheSame(oldItem: DriverPostModel, newItem: DriverPostModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DriverPostModel,
                                            newItem: DriverPostModel) =
                oldItem == newItem

        }
    }

}