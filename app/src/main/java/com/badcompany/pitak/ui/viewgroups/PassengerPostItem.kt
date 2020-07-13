package com.badcompany.pitak.ui.viewgroups

import android.view.View
import com.badcompany.domain.domainmodel.PassengerPost
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.addcar.MyItemClickListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_passenger_post.view.*

class PassengerPostItem(val post: PassengerPost, val myItemClickListener: MyItemClickListener) :
    Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.date.text = post.departureDate
        viewHolder.itemView.from.text = post.from.regionName
        viewHolder.itemView.to.text = post.to.regionName
        viewHolder.itemView.price.text =
            viewHolder.itemView.context.getString(R.string.price_and_seats_format,
                                                  post.price.toString(), post.seat.toString())
//        viewHolder.itemView.seats.text = post.seat.toString()

        if (!post.remark.isBlank()) {
            viewHolder.itemView.note.visibility = View.VISIBLE
            viewHolder.itemView.note.text = post.remark
        } else {
            viewHolder.itemView.note.visibility = View.GONE
        }

    }

    override fun getLayout() = R.layout.item_passenger_post
}
