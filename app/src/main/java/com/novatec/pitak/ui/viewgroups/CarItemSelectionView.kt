package com.novatec.pitak.ui.viewgroups

import androidx.core.view.isVisible
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.pitak.R
import com.novatec.pitak.ui.addcar.MyItemClickListener
import com.novatec.pitak.util.load
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_car_selection.view.*

class CarItemSelectionView(val car: CarDetails, val onItemClickListener: MyItemClickListener) :
    Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.carAvatar.load(car.image!!.link!!)
        viewHolder.itemView.plateNumber.text = car.carNumber
        viewHolder.itemView.carModel.text = car.carModel!!.name.toString()
        viewHolder.itemView.cardParent.setOnClickListener {
            onItemClickListener.onClick(position, it)
        }

        viewHolder.itemView.selectedStroke.isVisible = car.def

    }

    override fun getLayout() = R.layout.item_car_selection

}
