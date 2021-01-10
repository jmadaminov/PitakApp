package com.badcompany.pitak.ui.main.searchtrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.passenger_post.PassengerPostActivity
import com.badcompany.pitak.ui.passenger_post.offer_a_ride.ARG_PASSENGER_POST
import com.badcompany.pitak.util.loadCircleImageUrl
import com.badcompany.pitak.viewobjects.PassengerPostViewObj
import com.badcompany.remote.model.PassengerPostModel
import kotlinx.android.synthetic.main.item_passenger_post.view.*
import splitties.activities.start
import java.text.DecimalFormat

class PostFilterAdapter() :
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

                date.text = post.departureDate
                from.text = post.from.regionName
                to.text = post.to.regionName
                price.text = DecimalFormat("#,###").format(post.price) +" "+ itemView.context. getString(R.string.sum)
                seats.text = post.seat.toString()

                if (!post.remark.isBlank()) {
                    note.visibility = View.VISIBLE
                    note.text = post.remark
                } else {
                    note.visibility = View.GONE
                }
                cardParent.setOnClickListener {
                    context.start<PassengerPostActivity> {
                        putExtra(ARG_PASSENGER_POST,
                                 PassengerPostViewObj.mapFromPassengerPostModel(post))
                    }
                }

                post.profileDTO?.let {
                    tvPassengerName.text = it.name + " " + it.surname
                }

                post.profileDTO?.image?.link?.let {
                    ivPassenger.loadCircleImageUrl(it)
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