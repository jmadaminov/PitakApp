package com.novatec.pitak.ui.viewgroups

import android.view.View
import com.novatec.pitak.R
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_add_photo.view.*

class ItemAddCar(val clickListener: OnItemClickListener, var isLoading: Boolean = false) :
    Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.card_photo_grid.setOnClickListener {
            if (isLoading) return@setOnClickListener
            clickListener.onItemClick(this, it)
        }

        if (isLoading) {
            viewHolder.itemView.progress.visibility = View.VISIBLE
            viewHolder.itemView.layoutAdd.visibility = View.INVISIBLE
        } else {
            viewHolder.itemView.progress.visibility = View.INVISIBLE
            viewHolder.itemView.layoutAdd.visibility = View.VISIBLE
        }
    }

    override fun getLayout() = R.layout.item_add_car

}
