package com.novatec.pitak.ui.main.mytrips.historytrips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.novatec.pitak.R
import com.novatec.pitak.ui.driver_post.EXTRA_POST_ID
import com.novatec.pitak.ui.history_post.HistoryPostActivity
import com.novatec.remote.model.DriverPostModel
import kotlinx.android.synthetic.main.item_history_post.view.*
import splitties.activities.start
import java.text.DecimalFormat

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

                date.text = post.departureDate
                from.text = post.from.regionName
                to.text = post.to.regionName
                price.text =
                    DecimalFormat("#,###").format(post.price) + " " + itemView.context.getString(R.string.sum)
                seats.text = post.seat.toString()

                post.remark?.also {
                    note.visibility = View.VISIBLE
                    note.text = post.remark
                } ?: run {
                    note.visibility = View.GONE
                }
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