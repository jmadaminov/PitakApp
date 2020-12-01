package com.badcompany.pitak.ui.viewgroups

import android.view.View
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.driver_post.DriverPostActivity
import com.badcompany.pitak.ui.driver_post.EXTRA_POST_ID
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_active_post.view.*
import splitties.activities.start


class ActivePostItem(var post: DriverPost, val onClick: () -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.date.text = post.departureDate
        viewHolder.itemView.from.text = post.from.regionName
        viewHolder.itemView.to.text = post.to.regionName
        viewHolder.itemView.price.text = post.price.toString()
        viewHolder.itemView.status.text =
            (post.seat - post.availableSeats!!).toString() + "/" + post.seat.toString() + "   " + viewHolder.itemView.context.getString(
                R.string.active)

        if (!post.remark.isBlank()) {
            viewHolder.itemView.note.visibility = View.VISIBLE
            viewHolder.itemView.note.text = post.remark
        } else {
            viewHolder.itemView.note.visibility = View.GONE
        }

        if (viewHolder.itemView.findViewById<View>(R.id.progress) != null) {
            viewHolder.itemView.cl_parent.removeView(viewHolder.itemView.findViewById(R.id.progress))
        }

        viewHolder.itemView.cardParent.setOnClickListener {
            onClick()
        }

    }

    override fun getLayout() = R.layout.item_active_post
}
