package com.badcompany.pitak.ui.viewgroups

import android.view.View
import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.interfaces.IOnPostActionListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_active_post.view.*

class HistoryPostItem(var post: DriverPost, var onPostActionListener: IOnPostActionListener) :
    Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.date.text = post.departureDate
        viewHolder.itemView.from.text = post.from.nameUz
        viewHolder.itemView.to.text = post.to.nameUz
        viewHolder.itemView.price.text = post.price.toString()
        viewHolder.itemView.seats.text = post.seat.toString()

        if (!post.remark.isBlank()) {
            viewHolder.itemView.note.visibility = View.VISIBLE
            viewHolder.itemView.note.text = post.remark
        } else {
            viewHolder.itemView.note.visibility = View.GONE
        }


    }

    override fun getLayout() = R.layout.item_history_post
}
