package com.novatec.pitak.ui.main.searchtrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.novatec.pitak.R
import com.novatec.pitak.ui.passenger_post.PassengerPostActivity
import com.novatec.pitak.ui.passenger_post.offer_a_ride.ARG_PASSENGER_POST
import com.novatec.pitak.util.loadCircleImageUrl
import com.novatec.pitak.viewobjects.PassengerPostViewObj
import com.novatec.remote.model.PassengerPostModel
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

                date.text =  post.departureDate
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
                price.text = DecimalFormat("#,###").format(post.price) +" "+ itemView.context. getString(R.string.sum)
                seats.text = post.seat.toString()

                post.remark?.also {
                    note.visibility = View.VISIBLE
                    note.text = post.remark
                } ?: run {
                    note.visibility = View.GONE
                }
                cardParent.setOnClickListener {
                    context.start<PassengerPostActivity> {
                        putExtra(ARG_PASSENGER_POST,
                                 PassengerPostViewObj.mapFromPassengerPostModel(post))
                    }
                }

                post.profile?.let {
                    tvPassengerName.text = it.name + " " + it.surname
                }

                post.profile?.image?.link?.let {
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