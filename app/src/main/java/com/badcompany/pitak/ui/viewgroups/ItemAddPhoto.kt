package com.badcompany.pitak.ui.viewgroups

import com.badcompany.pitak.R
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_add_photo.view.*

class ItemAddPhoto(val clickListener: OnItemClickListener) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.card_photo_grid.setOnClickListener {
            clickListener.onItemClick(this, it)
        }

    }

    override fun getLayout() = R.layout.item_add_photo
}
